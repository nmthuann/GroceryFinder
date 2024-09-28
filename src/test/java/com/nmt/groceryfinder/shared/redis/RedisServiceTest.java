package com.nmt.groceryfinder.shared.redis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;

import static org.mockito.Mockito.verify;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 9/28/2024
 */

public class RedisServiceTest {
    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @InjectMocks
    private RedisService redisService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldDeleteKey_WhenClearCacheIsCalled(){
        String key = "testKey";
        redisService.clearCache(key);
        // Xác minh rằng phương thức delete được gọi với key đúng
        verify(redisTemplate).delete(key);
    }
}
