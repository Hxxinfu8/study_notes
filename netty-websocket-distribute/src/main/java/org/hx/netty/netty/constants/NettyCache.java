package org.hx.netty.netty.constants;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Upoint0002
 */
public class NettyCache {
    public static Map<String, Channel> channelMap = new ConcurrentHashMap<>();

    public static Map<String, String> one2One = new ConcurrentHashMap<>();
}
