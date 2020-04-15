package handler;

import com.sun.javafx.binding.StringFormatter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import util.FileTypeUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class WebSocketHandler extends SimpleChannelInboundHandler<Object> {
    private WebSocketServerHandshaker handshake;
    private File file;
    private FileOutputStream outputStream;
    private AtomicInteger connectNum;

    public WebSocketHandler(AtomicInteger connectNum) {
        this.connectNum = connectNum;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        if (o instanceof FullHttpRequest) {
            handleHttpRequest(channelHandlerContext, (FullHttpRequest) o);
            return;
        }

        if (o instanceof PingWebSocketFrame) {
            channelHandlerContext.channel().writeAndFlush(new PongWebSocketFrame(((PingWebSocketFrame) o).content().retain()));
            return;
        }
        if (o instanceof TextWebSocketFrame) {
            System.out.println(((TextWebSocketFrame) o).text());
            return;
        }

        if (o instanceof CloseWebSocketFrame) {
            handshake.close(channelHandlerContext.channel(), ((CloseWebSocketFrame) o).retain());
            return;
        }
        if (o instanceof BinaryWebSocketFrame) {
            ByteBuf buf = ((BinaryWebSocketFrame)o).content();
            byte[] b = new byte[3];
            buf.getBytes(0, b);
            String fileType = FileTypeUtil.getFileType(b);
            file  = new File(StringFormatter.format("E://nettyFile/%s.%s", UUID.randomUUID().toString(), fileType).getValue());
            outputStream = new FileOutputStream(file, true);
            channelHandlerContext.channel().writeAndFlush(((BinaryWebSocketFrame) o).retain());

            //buf.resetReaderIndex();
            byte[] bytes = new byte[buf.writerIndex()];
            buf.getBytes(0,bytes);
            outputStream.write(bytes);
            outputStream.flush();
        }

        if (o instanceof ContinuationWebSocketFrame) {
            channelHandlerContext.channel().writeAndFlush(((ContinuationWebSocketFrame) o).retain());
            ByteBuf buf = ((ContinuationWebSocketFrame)o).content();
            //buf.resetReaderIndex();
            byte[] bytes = new byte[buf.writerIndex()];
            buf.getBytes(0,bytes);
            outputStream.write(bytes);
            outputStream.flush();
        }

        if (((WebSocketFrame)o).isFinalFragment()) {
            outputStream.close();
        }

    }

    // unused
    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request) {
        if (request.decoderResult().isFailure() || !request.headers().get(HttpHeaderNames.UPGRADE).equals(HttpHeaderValues.WEBSOCKET.toString())) {
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST);
            sendHttpResponse(ctx, request, response);
            return;
        }
        System.out.println(request.content());

        WebSocketServerHandshakerFactory factory = new WebSocketServerHandshakerFactory("ws://" + request.headers().get(HttpHeaderNames.HOST),
                null, false, 64 * 1024 * 1024);
        handshake = factory.newHandshaker(request);
        if (handshake == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            handshake.handshake(ctx.channel(), request);
        }
    }

    // unused
    private void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest request, FullHttpResponse response) {
        if (!response.status().equals(HttpResponseStatus.OK)) {
            ByteBuf buf = Unpooled.copiedBuffer(response.status().codeAsText(), CharsetUtil.UTF_8);
            response.content().writeBytes(buf);
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        }

        ChannelFuture future = ctx.channel().writeAndFlush(response);
        if (!HttpUtil.isKeepAlive(request) || !response.status().equals(HttpResponseStatus.OK)) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(connectNum.incrementAndGet());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        System.out.println(connectNum.decrementAndGet());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("客户端连接断开");
    }
}
