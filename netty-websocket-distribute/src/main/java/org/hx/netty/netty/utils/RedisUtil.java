package org.hx.netty.netty.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author Upoint0002
 */
@Component
public class RedisUtil {
    private static StringRedisTemplate redisTemplate;

    private static final String ONE_2_ONE = "hx:websocket:one2one_";
    private static final String WAITING = "hx:websocket:waiting";
    private static final String CUSTOMER_INFO = "hx:websocket:waiting:customer_";
    private static final String STAFF = "hx:websocket:staff";
    private static final String CUSTOMER = "hx:websocket:customer";
    private static final String MESSAGE_QUEUE = "hx:websocket:message:queue_";

    @Autowired
    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        RedisUtil.redisTemplate = redisTemplate;
    }

    public static Set<String> getOne2One(String id) {
        return redisTemplate.opsForSet().members(ONE_2_ONE + id);
    }

    public static Set<String> getWaiting() {
        return redisTemplate.opsForSet().members(WAITING);
    }

    public static String getWaitingCustomerInfo(String id) {
        return redisTemplate.opsForValue().get(CUSTOMER_INFO + id);
    }

    public static List<String> getMessageQueue(String id) {
        Long size = redisTemplate.opsForList().size(MESSAGE_QUEUE + id);
        if (Objects.isNull(size)) {
            return null;
        }
        return redisTemplate.opsForList().range(MESSAGE_QUEUE + id, 0, size - 1);
    }

    public static void addCustomer(String id) {
        redisTemplate.opsForSet().add(CUSTOMER, id);
    }
    public static void addStaff(String id) {
        redisTemplate.opsForSet().add(STAFF, id);
    }
    public static void addWaiting(String id) {
        redisTemplate.opsForSet().add(WAITING, id);
    }
    public static void addOne2One(String staffId, String id) {
        redisTemplate.opsForSet().add(ONE_2_ONE + staffId, id);
    }

    public static void addWaitingCustomerInfo(String id, String info) {
        redisTemplate.opsForValue().set(CUSTOMER_INFO + id, info);
    }

    public static void addOfflineMessageQueue(String id, String message) {
        redisTemplate.opsForList().rightPush(MESSAGE_QUEUE + id, message);
    }

    public static void removeCustomer(String id) {
        redisTemplate.opsForSet().remove(CUSTOMER, id);
    }
    public static void removeStaff(String id) {
        redisTemplate.opsForSet().remove(STAFF, id);
    }
    public static void removeWaiting(String id) {
        redisTemplate.opsForSet().remove(WAITING, id);
    }
    public static void removeOne2One(String staffId, String id) {
        redisTemplate.opsForSet().remove(ONE_2_ONE + staffId, id);
    }

    public static void removeWaitingCustomerInfo(String id) {
        redisTemplate.delete(CUSTOMER_INFO + id);
    }
    public static void removeOfflineMessageQueue(String id) {
        redisTemplate.delete(MESSAGE_QUEUE + id);
    }

    public static Boolean containsCustomer(String id) {
        return redisTemplate.opsForSet().isMember(CUSTOMER, id);
    }
    public static Boolean containsStaff(String id) {
        return redisTemplate.opsForSet().isMember(STAFF, id);
    }
    public static Boolean containsWaiting(String id) {
        return redisTemplate.opsForSet().isMember(WAITING, id);
    }
    public static Boolean containsOne2One(String staffId, String id) {
        return redisTemplate.opsForSet().isMember(ONE_2_ONE + staffId, id);
    }

    public static Boolean hasStaffOnline() {
        Long size = redisTemplate.opsForSet().size(STAFF);
        if (size == null) {
            return false;
        }
        return size > 0;
    }
}
