package Square;

import java.util.HashMap;
import java.util.Map;

public class RateLimiter {
    static class RateConfig {
        String CID;
        int limit;
        int seconds;

        public RateConfig(String CID, int limit, int seconds) {
            this.CID = CID;
            this.limit = limit;
            this.seconds = seconds;
        }
    }

    // key = CID+timestamp sai_2, sai_3
    Map<String, Integer> rateCounter = new HashMap<>();
    Map<String, RateConfig> rateConfigMap = new HashMap<>();

    boolean shouldApproveRequest(String cid, long timestamp) {
        RateConfig rateConfig = rateConfigMap.get(cid);
        int limit = rateConfig.limit;
        int seconds = rateConfig.seconds;
        // seconds = 3
        // limit = 2
        // 1 2 3 4 5
        // 1 1 1
        // 1 2 2 1
        // 1 2 3 2 1
        for (long i = (timestamp - seconds + 1); i <= timestamp ; i++) {
            Integer currentTrafficThisSecond = rateCounter.getOrDefault(cid + i, 0);
            if (currentTrafficThisSecond >= limit) {
                return false;
            } else {
                rateCounter.put(cid + i, currentTrafficThisSecond + 1);
            }
        }
        System.out.println(rateCounter);
        return true;
    }

    public void setRateConfigMap(RateConfig rateConfig) {
        this.rateConfigMap.put(rateConfig.CID, rateConfig);
    }

    public static void main(String[] args){
        RateLimiter rateLimiter = new RateLimiter();
        RateConfig sai = new RateConfig("sai", 3, 2);
        rateLimiter.setRateConfigMap(sai);
        RateConfig li = new RateConfig("li", 1, 2);
        rateLimiter.setRateConfigMap(li);
        System.out.println("sai, 1  -- expected true -- got " + rateLimiter.shouldApproveRequest("sai", 1));
        System.out.println("sai, 1  -- expected true -- got " + rateLimiter.shouldApproveRequest("sai", 1));
        System.out.println("sai, 1  -- expected true -- got " + rateLimiter.shouldApproveRequest("sai", 1));
        System.out.println("sai, 1  -- expected false -- got " + rateLimiter.shouldApproveRequest("sai", 1));
        System.out.println("sai, 1  -- expected false -- got " + rateLimiter.shouldApproveRequest("sai", 1));
        System.out.println("sai, 1  -- expected false -- got " + rateLimiter.shouldApproveRequest("sai", 1));
        System.out.println("li,  1  -- expected true -- got " + rateLimiter.shouldApproveRequest("li", 1));
        System.out.println("li,  1  -- expected false -- got " + rateLimiter.shouldApproveRequest("li", 1));
        System.out.println("li,  1  -- expected false -- got " + rateLimiter.shouldApproveRequest("li", 1));
        System.out.println("li,  1  -- expected false -- got " + rateLimiter.shouldApproveRequest("li", 1));
        System.out.println("sai, 2  -- expected false -- got " + rateLimiter.shouldApproveRequest("sai", 2));
        System.out.println("sai, 3  -- expected true -- got " + rateLimiter.shouldApproveRequest("sai", 3));
        System.out.println("sai, 4  -- expected true -- got " + rateLimiter.shouldApproveRequest("sai", 4));
        System.out.println("li,  2  -- expected false -- got " + rateLimiter.shouldApproveRequest("li", 2));
        System.out.println("li,  3  -- expected true -- got " + rateLimiter.shouldApproveRequest("li", 3));
    }
}
