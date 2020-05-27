package org.hx.netty.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.hx.netty.netty.initializer.WebSocketInitializer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Upoint0002
 */
@SpringBootApplication
@Slf4j
public class NettyWebsocketDistributeApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(NettyWebsocketDistributeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>())
                .execute(() -> {
                    log.info("------------------------netty starting------------------------");
                    EventLoopGroup boss = new NioEventLoopGroup();
                    EventLoopGroup worker = new NioEventLoopGroup();

                    try {
                        ServerBootstrap serverBootstrap = new ServerBootstrap();
                        serverBootstrap.group(boss, worker)
                                .channel(NioServerSocketChannel.class)
                                .childHandler(new LoggingHandler())
                                .childHandler(new WebSocketInitializer());

                        Channel channel = serverBootstrap.bind(8091).channel();
                        channel.closeFuture().sync();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        boss.shutdownGracefully();
                        worker.shutdownGracefully();
                    }
                });
    }
}
