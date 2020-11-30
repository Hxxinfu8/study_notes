package org.hx.netty.study.ten;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @author Upoint0002
 */
public class MyServerHandler extends SimpleChannelInboundHandler<PersonProtocol> {
    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PersonProtocol msg) throws Exception {
        System.out.println("服务器接收消息长度： " + msg.getLength());
        System.out.println("服务器接收消息： " + new String(msg.getContent(), StandardCharsets.UTF_8));
        System.out.println(++this.count);

        byte[] responseMsg = UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8);
        PersonProtocol personProtocol = new PersonProtocol();
        personProtocol.setLength(responseMsg.length);
        personProtocol.setContent(responseMsg);
        ctx.channel().writeAndFlush(personProtocol);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
