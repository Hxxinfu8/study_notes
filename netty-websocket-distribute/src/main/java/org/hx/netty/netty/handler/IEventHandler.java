package org.hx.netty.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import org.hx.netty.netty.constants.NettyVO;

/**
 * @author Upoint0002
 */
public interface IEventHandler {
    /**
     * 事件处理
     * @param context
     * @param vo
     */
    void handle(ChannelHandlerContext context, NettyVO vo);
}
