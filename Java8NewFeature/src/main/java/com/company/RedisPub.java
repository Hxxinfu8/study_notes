package com.company;

import redis.clients.jedis.Jedis;

import java.util.Scanner;

public class RedisPub {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        while (true) {
            Scanner scanner = new Scanner(System.in);
            if(scanner.hasNext()) {
                jedis.publish("channel", scanner.next());
            }
        }
    }
}
