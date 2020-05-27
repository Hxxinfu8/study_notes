package org.hx.netty.netty.handler.impl;

import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang3.StringUtils;
import org.hx.netty.netty.constants.Constant;
import org.hx.netty.netty.constants.NettyCache;
import org.hx.netty.netty.constants.NettyCodeEnum;
import org.hx.netty.netty.constants.NettyVO;
import org.hx.netty.netty.handler.IEventHandler;
import org.hx.netty.netty.utils.RedisUtil;
import org.hx.netty.netty.utils.TopicPublisher;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Upoint0002
 */
public class SingleSendHandler implements IEventHandler {
    @Override
    public void handle(ChannelHandlerContext context, NettyVO vo) {
        if (StringUtils.isBlank(vo.getTo())) {
            NettyVO.sendMessage(context.channel(), NettyCodeEnum.ERROR);
            return;
        }
        // 是否访客发送的消息
        boolean isClient = NettyCodeEnum.CLIENT_ONLINE.getCode().equals(context.channel().attr(Constant.NettyKey.TYPE).get());

        // 判断是否已经包含当前消息接收者的Channel
        boolean isExists = isClient ? RedisUtil.containsStaff(vo.getTo()) : RedisUtil.containsCustomer(vo.getTo());

        if (isExists) {
            if (NettyCache.channelMap.containsKey(vo.getTo())) {
                NettyVO.sendMessage(NettyCache.channelMap.get(vo.getTo()), vo);
            } else {
                TopicPublisher.singleSend(NettyVO.toJson(vo));
            }
        } else {
            if (isClient) {
                if (NettyCache.messageQueue.containsKey(vo.getTo())) {
                    NettyCache.messageQueue.get(vo.getTo()).offer(vo);
                } else {
                    Queue<NettyVO> queue = new LinkedList<>();
                    queue.offer(vo);
                    NettyCache.messageQueue.put(vo.getTo(), queue);
                }
                return;
            }
            sendOffline(context, vo);
        }
    }

    private void sendOffline(ChannelHandlerContext ctx, NettyVO nettyVO) {
        nettyVO.setType(NettyCodeEnum.OFFLINE.getCode());
        nettyVO.setMessage(NettyCodeEnum.OFFLINE.getType());
        NettyVO.sendMessage(ctx.channel(), nettyVO);
    }
}
