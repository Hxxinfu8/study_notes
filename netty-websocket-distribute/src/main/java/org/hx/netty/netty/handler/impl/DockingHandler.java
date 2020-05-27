package org.hx.netty.netty.handler.impl;

import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang3.StringUtils;
import org.hx.netty.netty.constants.Constant;
import org.hx.netty.netty.constants.NettyCodeEnum;
import org.hx.netty.netty.constants.NettyVO;
import org.hx.netty.netty.handler.IEventHandler;
import org.hx.netty.netty.utils.RedisUtil;
import org.hx.netty.netty.utils.TopicPublisher;

/**
 * @author Upoint0002
 */
public class DockingHandler implements IEventHandler {
    @Override
    public void handle(ChannelHandlerContext context, NettyVO vo) {
        String to = vo.getTo();
        if (StringUtils.isBlank(to)) {
            NettyVO.sendMessage(context.channel(), NettyCodeEnum.ERROR);
        }

        if (!RedisUtil.containsCustomer(to)) {
            vo.setType(NettyCodeEnum.OFFLINE.getCode());
            vo.setMessage(NettyCodeEnum.OFFLINE.getType());
            NettyVO.sendMessage(context.channel(), vo);
            return;
        }

        if (RedisUtil.containsWaiting(to)) {
            // 告诉客户我接受你了
            TopicPublisher.singleSend(NettyVO.toJson(vo));

            // 提示自己接受客户成功了
            vo.setType(NettyCodeEnum.DOCK_SUCCESS.getCode());
            vo.setMessage(NettyCodeEnum.DOCK_SUCCESS.getType());
            NettyVO.sendMessage(context.channel(), vo);

            // 告诉其他客服客户已被我接受
            vo.setType(NettyCodeEnum.CLIENT_HAS_DOCK.getCode());
            vo.setMessage(NettyCodeEnum.CLIENT_HAS_DOCK.getType());
            vo.setStaffId(context.channel().attr(Constant.NettyKey.ID).get());
            TopicPublisher.dockingSuccess(NettyVO.toJson(vo));

        } else {
            vo.setType(NettyCodeEnum.CLIENT_HAS_DOCK.getCode());
            vo.setMessage(NettyCodeEnum.CLIENT_HAS_DOCK.getType());
            NettyVO.sendMessage(context.channel(), vo);
        }
    }
}
