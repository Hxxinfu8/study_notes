package com.hx.security.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 缓存工具类
 *
 * @author hx
 */
@Component
public class RedisUtil {
    @Value("${cache.prefix}")
    private String prefix;

    private final StringRedisTemplate redisTemplate;

    public RedisUtil(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setCacheObject(String key, String value, long expireTime) {
        redisTemplate.opsForValue().set(prefix + key, value, expireTime, TimeUnit.MINUTES);
    }

    public String getCacheObject(String key) {
        return redisTemplate.opsForValue().get(prefix + key);
    }

    public void deleteCacheObject(String key) {
        redisTemplate.delete(prefix + key);
    }
}
