package initializer;

import handler.WebSocketHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Upoint0002
 */
public class WebSocketInitializer extends ChannelInitializer {
    private static AtomicInteger atomicInteger = new AtomicInteger(0);
    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline()
                // HttpRequestDecoder和HttpResponseEncoder的一个组合，针对http协议进行编解码
                .addLast("http-codec", new HttpServerCodec())
                .addLast("http-aggregator", new HttpObjectAggregator(10240))
                .addLast("http-chunked", new ChunkedWriteHandler())
                // webSocket 数据压缩扩展，当添加这个的时候WebSocketServerProtocolHandler的第三个参数需要设置成true
                //.addLast(new WebSocketServerCompressionHandler())
                //.addLast(new WebSocketServerProtocolHandler("/ws", null, true, 1024 * 1024 * 10))
                .addLast(new WebSocketHandler(atomicInteger));
    }
}
