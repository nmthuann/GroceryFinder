package com.nmt.groceryfinder.shared.ratelimit;

import com.nmt.groceryfinder.shared.redis.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 11/29/2024
 */
@Component
public class RateLimiterImpl {
    private final IRedisService redisService;

    @Autowired
    public RateLimiterImpl(IRedisService redisService) {
        this.redisService = redisService;
    }

    public Boolean isAllowed(String key, int limit, long ttl) {
        long currentTime = Instant.now().toEpochMilli();
        String redisKey = "rate_limiter:" + key;
        List<Long> requestTimes = (List<Long>) redisService.getCache(redisKey);
        if (requestTimes == null) {
            requestTimes = new ArrayList<>();
        }

        requestTimes.removeIf(timestamp -> (currentTime - timestamp) > ttl);
        if (requestTimes.size() < limit) {
            requestTimes.add(currentTime);
            redisService.setCacheWithExpiration(redisKey, requestTimes, (int) (ttl / 1000));
            return true;
        }
        return false;
    }


}
