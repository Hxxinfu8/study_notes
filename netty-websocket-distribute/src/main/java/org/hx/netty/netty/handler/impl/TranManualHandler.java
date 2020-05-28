package org.hx.netty.netty.handler.impl;

import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang3.StringUtils;
import org.hx.netty.netty.constants.NettyCodeEnum;
import org.hx.netty.netty.constants.NettyVO;
import org.hx.netty.netty.handler.IEventHandler;
import org.hx.netty.netty.utils.RedisUtil;
import org.hx.netty.netty.utils.TopicPublisher;

import java.util.Date;

/**
 * @author Upoint0002
 */
public class TranManualHandler implements IEventHandler {
    @Override
    public void handle(ChannelHandlerContext context, NettyVO vo) {
        if (StringUtils.isBlank(vo.getCustomerId())) {
            NettyVO.sendMessage(context.channel(), NettyCodeEnum.ERROR);
            return;
        }


        if (!RedisUtil.hasStaffOnline()) {
            vo.setType(NettyCodeEnum.DOCK_FAIL.getCode());
            vo.setMessage("目前无在线客服");
            NettyVO.sendMessage(context.channel(), vo);
            return;
        }

        // 进入等待队列
        vo.setType(NettyCodeEnum.CLIENT_ACTIVE_SUCCESS.getCode());
        vo.setCreateTime(new Date());
        RedisUtil.addWaiting(vo.getCustomerId());
        RedisUtil.addWaitingCustomerInfo(vo.getCustomerId(), NettyVO.toJson(vo));

        TopicPublisher.transBroadCast(NettyVO.toJson(vo));
        NettyVO.sendMessage(context.channel(), vo);
    }
}
