package server;

import initializer.HttpFTPInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyFTPService {
    public static void main(String[] args) {
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup workers = new NioEventLoopGroup();
        try{
            System.out.println("— — — — — — — — netty服务器启动 — — — — — — — —");
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss, workers)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new HttpFTPInitializer());
            Channel channel = serverBootstrap.bind(8888).channel();
            channel.closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            workers.shutdownGracefully();
        }
    }
}
