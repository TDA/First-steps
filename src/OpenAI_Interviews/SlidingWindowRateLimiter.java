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

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import static OpenAI_Interviews.TestHelpers.assertFalse;
import static OpenAI_Interviews.TestHelpers.assertTrue;
import static OpenAI_Interviews.TestHelpers.runTest;

record Request(int timestamp) {}

public class SlidingWindowRateLimiter {
    private static final Integer SECONDS_IN_MILLIS = 1000;
    Map<String, Limit> clientConfigs = new HashMap<>();
    Map<String, Deque<Request>> clientRequests = new HashMap<>();

    public SlidingWindowRateLimiter(Map<String, Limit> clientConfigs) {
        this.clientConfigs = clientConfigs;
    }

    boolean allowRequest(String clientId, int timestampMs) {
        Limit clientLimit = clientConfigs.get(clientId);
        if (clientLimit == null) return false;
        var interval = clientLimit.interval() * SECONDS_IN_MILLIS;
        var requestsAllowed = clientLimit.requestsAllowed();

        Deque<Request> requests = clientRequests.getOrDefault(clientId, new ArrayDeque<>());

        // Expire older events before checking - assumption is that events are monotonic w.r.t timestamps
        var earliestRequest = requests.peek();
        while (!requests.isEmpty() && earliestRequest != null && (timestampMs - earliestRequest.timestamp() >= interval)) {
            earliestRequest = requests.poll();
        }

        // if the capacity is still hit, means that this request is coming in a window of high traffic, reject.
        if (requests.size() >= requestsAllowed) {
            return false;
        }
        // below capacity: add the new request and return true
        requests.add(new Request(timestampMs));
        clientRequests.put(clientId, requests);
        return true;
    }

    public static void main(String[] args) {
        runTest("allows requests below the client limit", () -> {
            SlidingWindowRateLimiter limiter = new SlidingWindowRateLimiter(Map.of(
                    "client-a", new Limit(3, 10)
            ));

            assertTrue(limiter.allowRequest("client-a", 0), "first request should be allowed");
            assertTrue(limiter.allowRequest("client-a", 1_000), "second request should be allowed");
            assertTrue(limiter.allowRequest("client-a", 2_000), "third request should be allowed");
        });

        runTest("counts multiple requests in the same second independently", () -> {
            SlidingWindowRateLimiter limiter = new SlidingWindowRateLimiter(Map.of(
                    "client-a", new Limit(3, 10)
            ));

            assertTrue(limiter.allowRequest("client-a", 5_000), "first request at same timestamp should be allowed");
            assertTrue(limiter.allowRequest("client-a", 5_000), "second request at same timestamp should be allowed");
            assertTrue(limiter.allowRequest("client-a", 5_000), "third request at same timestamp should be allowed");
            assertFalse(limiter.allowRequest("client-a", 5_000), "fourth request at same timestamp should be blocked");
        });

        runTest("uses exact millisecond timestamps instead of rounded second buckets", () -> {
            SlidingWindowRateLimiter limiter = new SlidingWindowRateLimiter(Map.of(
                    "client-a", new Limit(1, 1)
            ));

            assertTrue(limiter.allowRequest("client-a", 999), "first request should be allowed");
            assertFalse(limiter.allowRequest("client-a", 1_001), "request 2ms later should still be inside the 1s window");
            assertTrue(limiter.allowRequest("client-a", 2_000), "request after original timestamp expires should be allowed");
        });

        runTest("allows requests again when old timestamps leave the sliding window", () -> {
            SlidingWindowRateLimiter limiter = new SlidingWindowRateLimiter(Map.of(
                    "client-a", new Limit(3, 10)
            ));

            assertTrue(limiter.allowRequest("client-a", 0), "first request should be allowed");
            assertTrue(limiter.allowRequest("client-a", 1_000), "second request should be allowed");
            assertTrue(limiter.allowRequest("client-a", 2_000), "third request should be allowed");
            assertFalse(limiter.allowRequest("client-a", 9_999), "request before first timestamp expires should be blocked");
            assertTrue(limiter.allowRequest("client-a", 10_000), "request at expiration boundary should be allowed");
        });

        runTest("rejected requests do not consume future capacity", () -> {
            SlidingWindowRateLimiter limiter = new SlidingWindowRateLimiter(Map.of(
                    "client-a", new Limit(2, 10)
            ));

            assertTrue(limiter.allowRequest("client-a", 0), "first request should be allowed");
            assertTrue(limiter.allowRequest("client-a", 1_000), "second request should be allowed");
            assertFalse(limiter.allowRequest("client-a", 2_000), "third request should be blocked");
            assertFalse(limiter.allowRequest("client-a", 9_999), "rejected request should not evict or consume capacity");
            assertTrue(limiter.allowRequest("client-a", 10_000), "capacity should free when the first accepted request expires");
        });

        runTest("tracks clients independently", () -> {
            SlidingWindowRateLimiter limiter = new SlidingWindowRateLimiter(Map.of(
                    "client-a", new Limit(2, 10),
                    "client-b", new Limit(2, 10)
            ));

            assertTrue(limiter.allowRequest("client-a", 0), "client-a first request should be allowed");
            assertTrue(limiter.allowRequest("client-a", 1_000), "client-a second request should be allowed");
            assertFalse(limiter.allowRequest("client-a", 2_000), "client-a third request should be blocked");

            assertTrue(limiter.allowRequest("client-b", 2_000), "client-b should have independent capacity");
            assertTrue(limiter.allowRequest("client-b", 3_000), "client-b second request should be allowed");
            assertFalse(limiter.allowRequest("client-b", 4_000), "client-b third request should be blocked");
        });

        runTest("blocks unknown clients", () -> {
            SlidingWindowRateLimiter limiter = new SlidingWindowRateLimiter(Map.of(
                    "client-a", new Limit(3, 10)
            ));

            assertFalse(limiter.allowRequest("missing-client", 0), "unknown clients should be blocked");
        });
    }
}
