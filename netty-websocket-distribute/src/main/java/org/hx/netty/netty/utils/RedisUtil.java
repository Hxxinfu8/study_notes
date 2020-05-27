package org.hx.netty.netty.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Upoint0002
 */
@Component
public class RedisUtil {
    private static StringRedisTemplate redisTemplate;

    @Autowired
    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        RedisUtil.redisTemplate = redisTemplate;
    }

    public static void addCustomer(String id) {
        redisTemplate.opsForSet().add("hx:websocket:customer", id);
    }

    public static void addStaff(String id) {
        redisTemplate.opsForSet().add("hx:websocket:staff", id);
    }

    public static void addWaiting(String id) {
        redisTemplate.opsForSet().add("hx:websocket:waiting", id);
    }

    public static void removeCustomer(String id) {
        redisTemplate.opsForSet().remove("hx:websocket:customer", id);
    }
    public static void removeStaff(String id) {
        redisTemplate.opsForSet().remove("hx:websocket:staff", id);
    }
    public static void removeWaiting(String id) {
        redisTemplate.opsForSet().remove("hx:websocket:waiting", id);
    }

    public static Boolean containsCustomer(String id) {
        return redisTemplate.opsForSet().isMember("hx:websocket:customer", id);
    }

    public static Boolean containsStaff(String id) {
        return redisTemplate.opsForSet().isMember("hx:websocket:staff", id);
    }

    public static Boolean containsWaiting(String id) {
        return redisTemplate.opsForSet().isMember("hx:websocket:waiting", id);
    }

    public static Boolean hasStaffOnline() {
        Long size = redisTemplate.opsForSet().size("hx:websocket:staff");
        if (size == null) {
            return false;
        }
        return size > 0;
    }
}
