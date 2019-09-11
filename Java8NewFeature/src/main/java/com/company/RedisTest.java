package com.company;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class RedisTest {
    private static final Integer LOCK_EXPIRE = 30;
    private static final Integer LOCK_TIMEOUT = 20;
    private static final String LOCK_PREFIX = "test";

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch threadsLatch = new CountDownLatch(2);
        String lockKey = "test12332";

        Runnable lockRunnable = () -> {
            String lockValue = "";
            try {
                start.await();
                lockValue = lock(lockKey);
                if (Objects.nonNull(lockValue)) {
                    System.out.println(String.format("lock successfully: %s %s",Thread.currentThread().getName(), lockValue));
                }
                TimeUnit.SECONDS.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                unlock(lockKey, lockValue);
                threadsLatch.countDown();
            }
        };

        Thread threadA = new Thread(lockRunnable, "threadA");
        Thread threadB = new Thread(lockRunnable, "threadB");

        threadA.start();
        threadB.start();

        start.countDown();

        threadsLatch.await();

        System.out.println("done");
    }


    public static String lock(String key) {
        Jedis jedis = new Jedis();
        String lock = LOCK_PREFIX + key;
        long beginTime = System.currentTimeMillis() + LOCK_TIMEOUT * 1000;
        boolean flag = false;
        while (true) {
            String acquire = jedis.set(lock, String.valueOf(beginTime), SetParams.setParams().nx().ex(LOCK_EXPIRE));
            if ("OK".equals(acquire)) {
                System.out.println(String.format("%s获得锁", Thread.currentThread().getName()));
                flag = true;
                break;
            }

            if (System.currentTimeMillis() >= beginTime) {
                flag = false;
                break;
            }
        }

        if (!flag) {
            System.out.println(String.format("%s不能获得锁", Thread.currentThread().getName()));
            return null;
        }

        return String.valueOf(beginTime);
    }

    private static void unlock(String key, String value) {
        Jedis jedis = new Jedis();
        try {
            String curVal = jedis.get(LOCK_PREFIX + key);
            if (Objects.nonNull(curVal) && curVal.equals(value)) {
                System.out.println(String.format("%s释放锁", Thread.currentThread().getName()));
                jedis.del(LOCK_PREFIX + key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
