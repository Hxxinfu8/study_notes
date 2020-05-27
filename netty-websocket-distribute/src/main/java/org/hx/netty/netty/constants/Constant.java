package org.hx.netty.netty.constants;

import io.netty.util.AttributeKey;

import java.time.LocalDateTime;

/**
 * @author Upoint0002
 */
public interface Constant {
    /**
     * 单独发送
     */
    String SINGLE_SEND = "SINGLE_SEND";

    /**
     * 广播形式
     */
    String TRAN_BROAD_CAST = "TRAN_BROAD_CAST";


    String DOCKING_SUCCESS = "DOCKING_SUCCESS";


    interface WebSocketFrameText {
        String PING = "ping";
        String PONG = "pong";
    }


    interface NettyKey {
        AttributeKey<String> ID = AttributeKey.valueOf("id");
        AttributeKey<Integer> TYPE = AttributeKey.valueOf("type");
        AttributeKey<LocalDateTime> TIME = AttributeKey.valueOf("time");
        AttributeKey<NettyVO> INFO = AttributeKey.valueOf("info");
    }

}
