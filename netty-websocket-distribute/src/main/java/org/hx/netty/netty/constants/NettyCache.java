package org.hx.netty.netty.constants;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Upoint0002
 */
public class NettyCache {
    public static Map<String, Channel> channelMap = new ConcurrentHashMap<>();

    /**
     * 客服离线消息队列
     * String 客服id
     */
    public static Map<String, Queue<NettyVO>> messageQueue = new ConcurrentHashMap<>();

}
