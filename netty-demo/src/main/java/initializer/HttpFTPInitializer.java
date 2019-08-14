package initializer;

import handler.HttpFileServeHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

public class HttpFTPInitializer extends ChannelInitializer<SocketChannel> {
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("http-decoder", new HttpRequestDecoder())
                .addLast("http-aggregator", new HttpObjectAggregator(65535))
                .addLast("http-encoder", new HttpResponseEncoder())
                .addLast("http-chunked", new ChunkedWriteHandler())
                .addLast("http-server", new HttpFileServeHandler());
    }
}
