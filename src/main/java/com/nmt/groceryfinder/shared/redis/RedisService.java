package com.nmt.groceryfinder.shared.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 8/20/2024
 */
@Service
public class RedisService implements IRedisService{

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void clearCache(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void setCache(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void setCacheWithExpiration(String key, Object data, int minute) {
        redisTemplate.opsForValue().set(key, data, Duration.ofMinutes(minute));

    }

    @Override
    public Object getCache(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public String generateKey(String module, String value, String method) {
        return module+":"+value+":"+method;
    }
}
