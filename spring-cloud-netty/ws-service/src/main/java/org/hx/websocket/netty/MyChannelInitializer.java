package org.hx.websocket.netty;


import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author hx
 */
public class MyChannelInitializer extends ChannelInitializer {
    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        // HttpServerCodec的作用是将请求和应答消息编码或者解码为HTTP消息;
        pipeline.addLast("http-coder", new HttpServerCodec())
                // Http消息组装,把多个消息转换为一个单一的FullHttpRequest或是FullHttpResponse
                .addLast("aggregator", new HttpObjectAggregator(64 * 1024 * 1024))
                // 支持处理异步发送大数据文件，但不占用过多的内存，防止发生内存泄漏
                .addLast("http-chunked", new ChunkedWriteHandler())
                // WebSocket通信支持
                // .addLast(new WebSocketServerCompressionHandler())
                // .addLast(new WebSocketServerProtocolHandler("/ws", null, true, 1024 * 1024 * 10))
                .addLast("handler", new MyMatchHandler());
    }
}
