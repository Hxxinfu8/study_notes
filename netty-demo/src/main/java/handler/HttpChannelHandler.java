package handler;

import com.sun.javafx.binding.StringFormatter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.EmptyByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

public class HttpChannelHandler extends SimpleChannelInboundHandler<Object> {
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                Unpooled.wrappedBuffer("Hello World".getBytes("utf-8")));
        HttpHeaders headers = response.headers();
        headers.set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=UTF-8");
        headers.set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        headers.set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);

        if (msg instanceof HttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;
            if (HttpMethod.GET.equals(request.method())) {
                System.out.println(request);
            }
        }

        if (msg instanceof HttpContent) {
            LastHttpContent content = (LastHttpContent) msg;
            ByteBuf byteBuf = content.content();


            if (byteBuf instanceof EmptyByteBuf) {
                System.out.println(StringFormatter.format("content%S", "无数据"));
            } else {
                byte[] bytes = new byte[byteBuf.readableBytes()];
                byteBuf.readBytes(bytes);
                System.out.println(byteBuf.toString());
            }
        }

        ctx.write(response);
        ctx.flush();
    }
}
