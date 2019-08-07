package com.company;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.config.Config;

public class RedisLockManage {
    private static Redisson redisson;
    private static Config config = new Config();

    static {
        config.useSingleServer().setAddress("127.0.0.1:6379");
        redisson = (Redisson)Redisson.create(config);
    }

    private static void lock () {
        RLock rLock = redisson.getLock("");
        rLock.lock();
    }
}
