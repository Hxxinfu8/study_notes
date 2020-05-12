package org.hx.netty.study.sixth;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.hx.protobuf.MyMessageInfo;

/**
 * @author Upoint0002
 */
public class MyServerHandler extends SimpleChannelInboundHandler<MyMessageInfo.MyMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyMessageInfo.MyMessage msg) throws Exception {
        MyMessageInfo.MyMessage.DataType dataType = msg.getDataType();
        if (dataType == MyMessageInfo.MyMessage.DataType.PersonType) {
            System.out.println(msg.getPerson().getAge());
        } else {
            System.out.println(msg.getCat().getName());
        }
    }
}
