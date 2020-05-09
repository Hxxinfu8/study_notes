package org.hx.netty.study.fifth;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolHandler;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.net.URI;

/**
 * @author Upoint0002
 */
public class WebSocketClientInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new HttpClientCodec())
                .addLast(new ChunkedWriteHandler())
                .addLast(new HttpObjectAggregator(1024 * 64))
                .addLast(new WebSocketClientProtocolHandler(new URI("ws://127.0.0.1:8080/ws"), WebSocketVersion.V13, null, false, new DefaultHttpHeaders(), 64 * 1024 , true, true, true ))
                .addLast(new WebSocketClientHandler());
    }
}
