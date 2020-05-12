package org.hx.netty.study.sixth;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.hx.protobuf.MyMessageInfo;

import java.util.Random;

/**
 * @author Upoint0002
 */
public class MyClientHandler extends SimpleChannelInboundHandler<MyMessageInfo.MyMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyMessageInfo.MyMessage msg) throws Exception {
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        int randInt = new Random().nextInt(2);
        if (0 == randInt) {
            MyMessageInfo.MyMessage myMessage = MyMessageInfo.MyMessage.newBuilder().setDataType(MyMessageInfo.MyMessage.DataType.PersonType)
                    .setPerson(MyMessageInfo.Person.newBuilder().setName("张三").setAge(20)).build();
            ctx.channel().writeAndFlush(myMessage);
        } else {
            MyMessageInfo.MyMessage myMessage = MyMessageInfo.MyMessage.newBuilder().setDataType(MyMessageInfo.MyMessage.DataType.CatType)
                    .setCat(MyMessageInfo.Cat.newBuilder().setName("大黄")).build();
            ctx.channel().writeAndFlush(myMessage);
        }


    }
}
