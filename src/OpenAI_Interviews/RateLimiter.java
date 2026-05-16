package OpenAI_Interviews;


// Full Problem: Implement a rate limiter that allows at most N requests per T interval per client.
//
//Interface:
//
//allowRequest(clientId) → boolean
// Extensions:
//
//Token bucket variant (refill rate + burst capacity)
//Global rate limit across all clients
//Distributed rate limiter (Redis-like, mention Lua scripting for atomicity)
//Weighted requests (different endpoints cost different tokens)

import java.util.HashMap;
import java.util.Map;

import static OpenAI_Interviews.TestHelpers.assertFalse;
import static OpenAI_Interviews.TestHelpers.assertTrue;
import static OpenAI_Interviews.TestHelpers.runTest;

record Limit (Integer requestsAllowed, Integer interval, Integer allowedCost) {}
public class RateLimiter {
    private static final Integer SECONDS_TO_MILLIS = 1000;
    // Assume this is statically passed in or is available from a config service to inject into our class.
    Map<String, Limit> clientConfigs = new HashMap<>();
    Map<String, Integer> clientRequestsCount = new HashMap<>();

    public RateLimiter(Map<String, Limit> configs) {
        this.clientConfigs = configs;
    }

    // A major assumption here is that timestamp interval is discrete integer numbers. if instead we went to milliseconds
    // or floating point for timestamp interval this particular implementation of sliding window will stop working.
    boolean allowRequest(String clientId, int timestampSeconds) {
        var clientConfig = clientConfigs.get(clientId);
        if (clientConfig == null) return false;
        // Sliding window checks
        var limit = clientConfig.requestsAllowed();
        var interval = clientConfig.interval();

        int counter = 0;
        for (int i = (timestampSeconds - interval + 1); i <= timestampSeconds; i++) {
            // Check requests in each window
            var requestsThatSecond = clientRequestsCount.getOrDefault(clientId+":"+i, 0);
            counter += requestsThatSecond;
            if (counter >= limit) {
                return false;
            }
        }
        // Allowed, increment and return true
        clientRequestsCount.put(clientId+":"+timestampSeconds, clientRequestsCount.getOrDefault(clientId, 0) + 1);

        return true;
    }

    public static void main(String[] args) {
        runTest("allows requests below the client limit", () -> {
            RateLimiter limiter = new RateLimiter(Map.of(
                    "client-a", new Limit(3, 10, 3)
            ));

            assertTrue(limiter.allowRequest("client-a", 0), "first request should be allowed");
            assertTrue(limiter.allowRequest("client-a", 1), "second request should be allowed");
            assertTrue(limiter.allowRequest("client-a", 2), "third request should be allowed");
        });

        runTest("blocks requests over the client limit within the window", () -> {
            RateLimiter limiter = new RateLimiter(Map.of(
                    "client-a", new Limit(3, 10, 3)
            ));

            assertTrue(limiter.allowRequest("client-a", 0), "first request should be allowed");
            assertTrue(limiter.allowRequest("client-a", 1), "second request should be allowed");
            assertTrue(limiter.allowRequest("client-a", 2), "third request should be allowed");
            assertFalse(limiter.allowRequest("client-a", 3), "fourth request inside 10s window should be blocked");
        });

        runTest("allows requests again after old timestamps leave the sliding window", () -> {
            RateLimiter limiter = new RateLimiter(Map.of(
                    "client-a", new Limit(3, 10, 3)
            ));

            assertTrue(limiter.allowRequest("client-a", 0), "first request should be allowed");
            assertTrue(limiter.allowRequest("client-a", 1), "second request should be allowed");
            assertTrue(limiter.allowRequest("client-a", 2), "third request should be allowed");
            assertFalse(limiter.allowRequest("client-a", 9), "request before window slides should be blocked");
            assertTrue(limiter.allowRequest("client-a", 10), "request at window boundary should be allowed");
        });

        runTest("tracks clients independently", () -> {
            RateLimiter limiter = new RateLimiter(Map.of(
                    "client-a", new Limit(2, 10, 2),
                    "client-b", new Limit(2, 10, 2)
            ));

            assertTrue(limiter.allowRequest("client-a", 0), "client-a first request should be allowed");
            assertTrue(limiter.allowRequest("client-a", 1), "client-a second request should be allowed");
            assertFalse(limiter.allowRequest("client-a", 2), "client-a third request should be blocked");

            assertTrue(limiter.allowRequest("client-b", 2), "client-b should have independent capacity");
            assertTrue(limiter.allowRequest("client-b", 3), "client-b second request should be allowed");
            assertFalse(limiter.allowRequest("client-b", 4), "client-b third request should be blocked");
        });

        runTest("supports different limits per client", () -> {
            RateLimiter limiter = new RateLimiter(Map.of(
                    "free-client", new Limit(1, 10, 1),
                    "paid-client", new Limit(3, 10, 3)
            ));

            assertTrue(limiter.allowRequest("free-client", 0), "free client first request should be allowed");
            assertFalse(limiter.allowRequest("free-client", 1), "free client second request should be blocked");

            assertTrue(limiter.allowRequest("paid-client", 0), "paid client first request should be allowed");
            assertTrue(limiter.allowRequest("paid-client", 1), "paid client second request should be allowed");
            assertTrue(limiter.allowRequest("paid-client", 2), "paid client third request should be allowed");
            assertFalse(limiter.allowRequest("paid-client", 3), "paid client fourth request should be blocked");
        });

        runTest("blocks unknown clients", () -> {
            RateLimiter limiter = new RateLimiter(Map.of(
                    "client-a", new Limit(3, 10, 3)
            ));

            assertFalse(limiter.allowRequest("missing-client", 0), "unknown clients should be blocked");
        });
    }
}
