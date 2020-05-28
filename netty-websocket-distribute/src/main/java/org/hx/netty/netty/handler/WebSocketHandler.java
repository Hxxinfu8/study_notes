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

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Upoint0002
 */
@Slf4j
public class WebSocketHandler extends SimpleChannelInboundHandler<Object> {
    private WebSocketServerHandshaker handshake;

    private static WebSocketEventHandlerFactory factory = new WebSocketEventHandlerFactory();

    private AtomicInteger connectNum;


    public WebSocketHandler (AtomicInteger connectNum) {
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

        if (o instanceof CloseWebSocketFrame) {
            handshake.close(channelHandlerContext.channel(), ((CloseWebSocketFrame) o).retain());
            return;
        }

        if (o instanceof WebSocketFrame) {
            handleWebSocketRequest(channelHandlerContext, (WebSocketFrame) o);
        }
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("连接总数:" + connectNum.incrementAndGet());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("连接总数:" + connectNum.decrementAndGet());
        offline(ctx);
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
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

    private void offline(ChannelHandlerContext context) {
        String id = context.channel().attr(Constant.NettyKey.ID).get();
        if (StringUtils.isBlank(id)) {
            return;
        }

        log.info("用户下线" + id);
        NettyCache.channelMap.remove(id);

        if (NettyCodeEnum.STAFF_ONLINE.getCode().equals(context.channel().attr(Constant.NettyKey.TYPE).get())) {
            RedisUtil.removeStaff(id);
        } else {
            RedisUtil.removeWaiting(id);
            RedisUtil.removeWaitingCustomerInfo(id);
            RedisUtil.removeCustomer(id);

            NettyVO nettyVO = new NettyVO();
            nettyVO.setCustomerId(id);
            nettyVO.setFrom(id);
            nettyVO.setType(NettyCodeEnum.WAITING_QUEUE_OFFLINE.getCode());
            nettyVO.setMessage(NettyCodeEnum.WAITING_QUEUE_OFFLINE.getType());
            TopicPublisher.transBroadCast(NettyVO.toJson(nettyVO));

            if (NettyCache.one2One.containsKey(id)) {
                nettyVO.setType(NettyCodeEnum.OFFLINE.getCode());
                nettyVO.setMessage(NettyCodeEnum.OFFLINE.getType());
                String staffId = NettyCache.one2One.get(id);
                if (NettyCache.channelMap.containsKey(staffId)) {
                    NettyVO.sendMessage(NettyCache.channelMap.get(staffId), nettyVO);
                } else {
                    nettyVO.setTo(staffId);
                    TopicPublisher.singleSend(NettyVO.toJson(nettyVO));
                }

                NettyCache.one2One.remove(id);
                RedisUtil.removeOne2One(staffId, id);
            }

        }

    }
}
