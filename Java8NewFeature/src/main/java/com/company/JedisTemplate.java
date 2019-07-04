package com.company;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class JedisTemplate {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.publish("channel", "你好");
        JedisPubSub jedisPubSub = new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                System.out.println(channel + ": " + message);
            }
        };
        jedis.subscribe(jedisPubSub, "channel");
    }
}
