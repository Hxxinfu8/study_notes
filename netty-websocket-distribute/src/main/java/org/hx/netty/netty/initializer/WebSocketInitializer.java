package org.hx.netty.netty.initializer;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.hx.netty.netty.handler.WebSocketHandler;

/**
 * @author Upoint0002
 */
public class WebSocketInitializer extends ChannelInitializer {
    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast("http-codec", new HttpServerCodec())
                .addLast("aggregator", new HttpObjectAggregator(64 * 1024))
                .addLast("http-chunked", new ChunkedWriteHandler())
                .addLast(new WebSocketHandler());
    }
}
