package org.hx.netty.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hx.netty.netty.constants.Constant;
import org.hx.netty.netty.constants.NettyCache;
import org.hx.netty.netty.constants.NettyCodeEnum;
import org.hx.netty.netty.constants.NettyVO;
import org.hx.netty.netty.utils.RedisUtil;
import org.hx.netty.netty.utils.TopicPublisher;

import java.net.SocketAddress;
import java.util.Objects;

/**
 * @author Upoint0002
 */
@Slf4j
public class WebSocketHandler extends SimpleChannelInboundHandler<Object> {
    private WebSocketServerHandshaker handshake;

    private String clientIp;

    private static WebSocketEventHandlerFactory factory = new WebSocketEventHandlerFactory();

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

        if (o instanceof CloseWebSocketFrame) {
            handshake.close(channelHandlerContext.channel(), ((CloseWebSocketFrame) o).retain());
            return;
        }

        if (o instanceof WebSocketFrame) {
            handleWebSocketRequest(channelHandlerContext, (WebSocketFrame) o);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        offline(ctx);
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        offline(ctx);
        ctx.close();
    }

    private void handleWebSocketRequest(ChannelHandlerContext ctx, WebSocketFrame frame) {
        String msg = ((TextWebSocketFrame) frame).text();

        if (Constant.WebSocketFrameText.PING.equals(msg)) {
            NettyVO.sendMessage(ctx.channel(), Constant.WebSocketFrameText.PONG);
            return;
        }

        try {
            NettyVO nettyVO = NettyVO.strJson2Netty(msg);

            if (Objects.nonNull(nettyVO)) {
                Integer type = nettyVO.getType();
                for (NettyCodeEnum codeEnum : NettyCodeEnum.values()) {
                    if (codeEnum.getCode().equals(type)) {
                        factory.setEventHandler(codeEnum);
                        factory.run(ctx, nettyVO);
                        break;
                    }
                }
            }


        } catch (Exception e) {
            log.warn(e.getMessage());
        }
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request) {
        if (request.decoderResult().isFailure() || !request.headers().get(HttpHeaderNames.UPGRADE).equals(HttpHeaderValues.WEBSOCKET.toString())) {
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST);
            sendHttpResponse(ctx, request, response);
            return;
        }
        clientIp = getIpAddressInWS(request.headers());
        if (clientIp == null) {
            SocketAddress address = ctx.channel().remoteAddress();
            if (address != null) {
                clientIp = address.toString().split(":")[0].replace("/", "");
            }
        }

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

    private String getIpAddressInWS(HttpHeaders headers) {
        String ip = headers.get("X-Forwarded-For");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            ip = ip.split(",")[0];
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.get("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.get("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.get("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.get("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.get("X-Real-IP");
        }
        return ip;
    }

    private void offline(ChannelHandlerContext context) {
        String id = String.valueOf(context.channel().attr(Constant.NettyKey.ID).get());
        if (StringUtils.isBlank(id)) {
            return;
        }

        log.info("用户下线" + id);
        NettyCache.channelMap.remove(id);

        if (NettyCodeEnum.STAFF_ONLINE.getCode().equals(context.channel().attr(Constant.NettyKey.TYPE).get())) {
            RedisUtil.removeStaff(id);
        } else {
            RedisUtil.removeWaiting(id);
            RedisUtil.removeCustomer(id);

            NettyVO nettyVO = new NettyVO();
            nettyVO.setCustomerId(id);
            nettyVO.setFrom(id);
            nettyVO.setType(NettyCodeEnum.WAITING_QUEUE_OFFLINE.getCode());
            nettyVO.setMessage(NettyCodeEnum.WAITING_QUEUE_OFFLINE.getType());

            TopicPublisher.transBroadCast(NettyVO.toJson(nettyVO));
        }

    }
}
