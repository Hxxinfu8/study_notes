package org.hx.netty.study.ten;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

/**
 * @author Upoint0002
 */
public class MyClientHandler extends SimpleChannelInboundHandler<PersonProtocol> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PersonProtocol msg) throws Exception {
        System.out.println("客户端接收消息长度： " + msg.getLength());
        System.out.println("客户端接收消息： " + new String(msg.getContent(), StandardCharsets.UTF_8));
    }

    /**
     * Calls {@link ChannelHandlerContext#fireChannelActive()} to forward
     * to the next {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
     * <p>
     * Sub-classes may override this method to change behavior.
     *
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        byte[] message = "send to server ".getBytes(StandardCharsets.UTF_8);
        for (int i = 0; i < 10; i++) {
            PersonProtocol personProtocol = new PersonProtocol();
            personProtocol.setLength(message.length);
            personProtocol.setContent(message);
            ctx.channel().writeAndFlush(personProtocol);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close().sync();
    }


}
