package org.hx.websocket.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;

/**
 * @author hx
 */
public class MyMatchHandler extends SimpleChannelInboundHandler<Object> {
    private WebSocketServerHandshaker handshake;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        if (o instanceof FullHttpRequest) {
            System.out.println("websocket首次连接，进入http握手");
            handleHttpRequest(channelHandlerContext, (FullHttpRequest) o);
            return;
        }

        if (o instanceof PingWebSocketFrame) {
            System.out.println("ping-----");
            channelHandlerContext.channel().writeAndFlush(new PongWebSocketFrame(((PingWebSocketFrame) o).content().retain()));
            return;
        }

        if (o instanceof TextWebSocketFrame) {
            String message = ((TextWebSocketFrame) o).text();
            System.out.println(message);
            channelHandlerContext.channel().writeAndFlush(new TextWebSocketFrame(message));
            return;
        }

        if (o instanceof CloseWebSocketFrame) {
            handshake.close(channelHandlerContext.channel(), ((CloseWebSocketFrame) o).retain());
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端连接: " + ctx.channel().id());
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端断开: " + ctx.channel().id());
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("客户端异常断开: " + ctx.channel().id());
        System.out.println("异常信息：" + cause.getMessage());
        super.exceptionCaught(ctx, cause);
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request) {
        if (request.decoderResult().isFailure() || !request.headers().get(HttpHeaderNames.UPGRADE).equals(HttpHeaderValues.WEBSOCKET.toString())) {
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST);
            sendHttpResponse(ctx, request, response);
            return;
        }

        // allowExtensions设置true允许添加query
        WebSocketServerHandshakerFactory factory = new WebSocketServerHandshakerFactory("ws://" + request.headers().get(HttpHeaderNames.HOST),
                null, true, 64 * 1024 * 1024);
        handshake = factory.newHandshaker(request);
        if (handshake == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            handshake.handshake(ctx.channel(), request);
        }
    }

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
}
