package org.hx.netty.netty.handler.impl;

import io.netty.channel.ChannelHandlerContext;
import org.hx.netty.netty.constants.Constant;
import org.hx.netty.netty.constants.NettyCache;
import org.hx.netty.netty.constants.NettyCodeEnum;
import org.hx.netty.netty.constants.NettyVO;
import org.hx.netty.netty.handler.IEventHandler;
import org.hx.netty.netty.utils.RedisUtil;
import org.hx.netty.netty.utils.TopicPublisher;

/**
 * @author Upoint0002
 */
public class CustomerOfflineHandler implements IEventHandler {
    @Override
    public void handle(ChannelHandlerContext context, NettyVO vo) {
        String id = context.channel().attr(Constant.NettyKey.ID).get();

        NettyVO.sendMessage(context.channel(), vo);

        if (NettyCache.one2One.containsKey(id)) {
            String staffId = NettyCache.one2One.get(id);
            NettyCache.one2One.remove(id);
            RedisUtil.removeOne2One(staffId, id);
            if (NettyCache.channelMap.containsKey(staffId)) {
                NettyVO.sendMessage(NettyCache.channelMap.get(staffId), vo);
            } else {
                vo.setTo(staffId);
                TopicPublisher.singleSend(NettyVO.toJson(vo));
            }
        }

        // 客户等待中
        if (RedisUtil.containsWaiting(id)) {
            RedisUtil.removeWaiting(id);
            RedisUtil.removeWaitingCustomerInfo(id);
            vo.setType(NettyCodeEnum.WAITING_QUEUE_OFFLINE.getCode());
            vo.setMessage(NettyCodeEnum.WAITING_QUEUE_OFFLINE.getType());
            TopicPublisher.transBroadCast(NettyVO.toJson(vo));
        }
    }
}
