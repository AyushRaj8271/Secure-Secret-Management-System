package com.mybackend.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class RateLimiter {
    private static final long RATE_LIMIT_WINDOW_MS = TimeUnit.MINUTES.toMillis(1); // 1 minute window
    private static final int MAX_REQUESTS_PER_MINUTE = 60;

    private ConcurrentHashMap<String, UserRequestInfo> userRequestMap = new ConcurrentHashMap<>();

    public boolean isRequestAllowed(String userId) {
        long currentTime = System.currentTimeMillis();
        UserRequestInfo userRequestInfo = userRequestMap.getOrDefault(userId, new UserRequestInfo(0, currentTime));

        if (currentTime - userRequestInfo.timestamp > RATE_LIMIT_WINDOW_MS) {
            userRequestInfo = new UserRequestInfo(0, currentTime);
        }

        if (userRequestInfo.requestCount < MAX_REQUESTS_PER_MINUTE) {
            userRequestInfo.requestCount++;
            userRequestMap.put(userId, userRequestInfo);
            return true;
        }

        return false;
    }

    private static class UserRequestInfo {
        int requestCount;
        long timestamp;

        UserRequestInfo(int requestCount, long timestamp) {
            this.requestCount = requestCount;
            this.timestamp = timestamp;
        }
    }
}
