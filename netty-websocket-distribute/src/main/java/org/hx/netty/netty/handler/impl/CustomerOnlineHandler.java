package org.hx.netty.netty.handler.impl;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hx.netty.netty.constants.Constant;
import org.hx.netty.netty.constants.NettyCache;
import org.hx.netty.netty.constants.NettyCodeEnum;
import org.hx.netty.netty.constants.NettyVO;
import org.hx.netty.netty.handler.IEventHandler;
import org.hx.netty.netty.utils.RedisUtil;

/**
 * @author Upoint0002
 */
@Slf4j
public class CustomerOnlineHandler implements IEventHandler {
    @Override
    public void handle(ChannelHandlerContext context, NettyVO vo) {
        String customerId = vo.getCustomerId();
        if (StringUtils.isBlank(customerId)) {
            NettyVO.sendMessage(context.channel(), NettyCodeEnum.ERROR);
            return;
        }
        log.info("访客" + customerId + "上线");

        NettyVO.setAttribute(context, Constant.NettyKey.ID, customerId);
        NettyVO.setAttribute(context, Constant.NettyKey.TYPE, NettyCodeEnum.CLIENT_ONLINE.getCode());

        RedisUtil.addCustomer(customerId);
        NettyCache.channelMap.put(customerId, context.channel());

        vo.setMessage(NettyCodeEnum.CLIENT_ONLINE.getType());

        NettyVO.sendMessage(context.channel(), vo);
    }
}
