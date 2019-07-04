package com.company;

import redis.clients.jedis.JedisPubSub;

public class MyJedisPubSub extends JedisPubSub {
    @Override
    public void onMessage(String channel, String message) {
        System.out.println(channel + ": " + message);
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        super.onPMessage(pattern, channel, message);
    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
        super.onSubscribe(channel, subscribedChannels);
    }
}
