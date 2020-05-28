package org.hx.netty.netty.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

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

    public static Set<String> getOne2One(String id) {
        return redisTemplate.opsForSet().members("hx:websocket:one2one_" + id);
    }

    public static Set<String> getWaiting() {
        return redisTemplate.opsForSet().members("hx:websocket:waiting");
    }

    public static String getWaitingCustomerInfo(String id) {
        return redisTemplate.opsForValue().get("hx:websocket:waiting:customer_" + id);
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
    public static void addOne2One(String staffId, String id) {
        redisTemplate.opsForSet().add("hx:websocket:one2one_" + staffId, id);
    }

    public static void addWaitingCustomerInfo(String id, String info) {
        redisTemplate.opsForValue().set("hx:websocket:waiting:customer_" + id, info);
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
    public static void removeOne2One(String staffId, String id) {
        redisTemplate.opsForSet().remove("hx:websocket:one2one_" + staffId, id);
    }

    public static void removeWaitingCustomerInfo(String id) {
        redisTemplate.delete("hx:websocket:waiting:customer_" + id);
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
    public static Boolean containsOne2One(String staffId, String id) {
        return redisTemplate.opsForSet().isMember("hx:websocket:one2one_" + staffId, id);
    }

    public static Boolean hasStaffOnline() {
        Long size = redisTemplate.opsForSet().size("hx:websocket:staff");
        if (size == null) {
            return false;
        }
        return size > 0;
    }
}
