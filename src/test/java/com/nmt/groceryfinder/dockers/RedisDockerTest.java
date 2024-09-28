package com.nmt.groceryfinder.dockers;

import com.nmt.groceryfinder.configs.RedisConfig;
import com.nmt.groceryfinder.shared.redis.IRedisService;
import com.nmt.groceryfinder.shared.redis.RedisService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 9/28/2024
 */
@SpringBootTest(classes = {RedisConfig.class,  RedisService.class})
@Testcontainers
public class RedisDockerTest {

    private static GenericContainer<?> redisContainer;
    @Autowired
    private IRedisService redisService;

    @BeforeAll
    public static void setUp() {
        redisContainer = new GenericContainer<>(DockerImageName.parse("redis:7.4.0-alpine"))
                .withExposedPorts(6379);
        redisContainer.start();
    }

    @AfterAll
    public static void tearDown() {
        // Stop the Redis container
        redisContainer.stop();
    }

    @Test
    public void testRedisConnection() {
        // Test Redis SET and GET
        String key = "testKey";
        String value = "testValue";

        redisService.setCache(key, value);
        Object result = redisService.getCache(key);

        assertEquals(value, result);
    }
}