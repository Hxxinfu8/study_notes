package org.hx.netty.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import org.hx.netty.netty.constants.NettyCodeEnum;
import org.hx.netty.netty.constants.NettyVO;
import org.hx.netty.netty.handler.impl.*;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Upoint0002
 */
public class WebSocketEventHandlerFactory {
    private IEventHandler eventHandler;

    private final static Map<NettyCodeEnum, IEventHandler> MAP;

    static {
        MAP = new ConcurrentHashMap<>();
        MAP.put(NettyCodeEnum.CLIENT_ONLINE, new CustomerOnlineHandler());
        MAP.put(NettyCodeEnum.STAFF_ONLINE, new StaffOnlineHandler());
        MAP.put(NettyCodeEnum.SINGLE_SEND, new SingleSendHandler());
        MAP.put(NettyCodeEnum.CLIENT_ACTIVE, new TranManualHandler());
        MAP.put(NettyCodeEnum.DOCKING, new DockingHandler());
        MAP.put(NettyCodeEnum.ONLINE_QUEUE, new ChattingQueueHandler());
        MAP.put(NettyCodeEnum.WAITING_QUEUE, new WaitingQueueHandler());
        MAP.put(NettyCodeEnum.CLIENT_OFFLINE, new CustomerOfflineHandler());
    }


    public void setEventHandler(NettyCodeEnum codeEnum) {
        eventHandler = MAP.get(codeEnum);
    }

    public void run(ChannelHandlerContext ctx, NettyVO nettyVO) {
        if (Objects.isNull(eventHandler)) {
            return;
        }
        eventHandler.handle(ctx, nettyVO);
    }
}
