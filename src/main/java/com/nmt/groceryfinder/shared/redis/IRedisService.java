package com.nmt.groceryfinder.shared.redis;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 8/20/2024
 */
public interface IRedisService {
    void clearCache(String key);
    void setCache(String key, Object data);
    void setCacheWithExpiration(String key, Object data,  int minute);
    Object getCache(String key);
    String generateKey(String module, String value, String method);
}
