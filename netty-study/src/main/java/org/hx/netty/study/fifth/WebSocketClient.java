package org.hx.netty.study.fifth;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Upoint0002
 */
public class WebSocketClient {
    public static void main(String[] args) {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new WebSocketClientInitializer());
            connect(bootstrap);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

    private static void connect(Bootstrap bootstrap) throws IOException {
        Channel channel = bootstrap.connect("127.0.0.1", 8080)
                .channel();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        for (;;) {
            String message = reader.readLine();
            if (!"".equals(message)) {
                channel.writeAndFlush(new TextWebSocketFrame(message + "\r\n"));
            }
        }
    }
}
