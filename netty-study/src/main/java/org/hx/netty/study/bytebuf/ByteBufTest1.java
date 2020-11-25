package org.hx.netty.study.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.StandardCharsets;

/**
 * @author hx
 */
public class ByteBufTest1 {
    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.copiedBuffer("张hello world", StandardCharsets.UTF_8);
        if (byteBuf.hasArray()) {
            System.out.println(new String(byteBuf.array(), StandardCharsets.UTF_8));
            System.out.println(byteBuf);
            System.out.println(byteBuf.arrayOffset());
            System.out.println(byteBuf.readerIndex());
            System.out.println(byteBuf.writerIndex());
            System.out.println(byteBuf.capacity());
            for (int i = 0; i < byteBuf.readableBytes(); i++) {
                System.out.println((char)byteBuf.getByte(i));
            }
            System.out.println(byteBuf.getCharSequence(0, byteBuf.readableBytes(), StandardCharsets.UTF_8));
        }
    }
}
