package com.company;

import redis.clients.jedis.Jedis;

public class RedisSub {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.subscribe(new MyJedisPubSub(), "channel");
    }
}
