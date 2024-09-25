package com.nmt.groceryfinder.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 9/25/2024
 */
public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T fromJson(String jsonString, Class<T> clazz) {
        try {
            return objectMapper.readValue(jsonString, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert JSON to object", e);
        }
    }
}
