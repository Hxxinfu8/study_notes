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
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Upoint0002
 */
@Slf4j
public class StaffOnlineHandler implements IEventHandler {
    @Override
    public void handle(ChannelHandlerContext context, NettyVO vo) {
        String staffId = vo.getStaffId();

        if (StringUtils.isBlank(staffId)) {
            NettyVO.sendMessage(context.channel(), NettyCodeEnum.ERROR);
            return;
        }

        log.info("客服" + staffId + "上线");

        NettyVO.setAttribute(context, Constant.NettyKey.ID, staffId);
        NettyVO.setAttribute(context, Constant.NettyKey.TYPE, NettyCodeEnum.STAFF_ONLINE.getCode());
        NettyVO.setAttribute(context, Constant.NettyKey.TIME, LocalDateTime.now());

        NettyCache.channelMap.put(staffId, context.channel());
        RedisUtil.addStaff(staffId);

        vo.setMessage(NettyCodeEnum.STAFF_ONLINE.getType());

        NettyVO.sendMessage(context.channel(), vo);

        List<String> messages = RedisUtil.getMessageQueue(staffId);

        if (!CollectionUtils.isEmpty(messages)) {
            for (String message : messages) {
                NettyVO.sendMessage(context.channel(), message);
            }
            RedisUtil.removeOfflineMessageQueue(staffId);
        }
    }
}
