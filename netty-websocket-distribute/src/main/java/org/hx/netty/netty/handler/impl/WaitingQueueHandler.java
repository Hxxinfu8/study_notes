package org.hx.netty.netty.handler.impl;

import com.alibaba.fastjson.JSONArray;
import io.netty.channel.ChannelHandlerContext;
import org.hx.netty.netty.constants.NettyVO;
import org.hx.netty.netty.handler.IEventHandler;
import org.hx.netty.netty.utils.RedisUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Upoint0002
 */
public class WaitingQueueHandler implements IEventHandler {
    @Override
    public void handle(ChannelHandlerContext context, NettyVO vo) {
        Set<String> waiters= RedisUtil.getWaiting();
        List<NettyVO> infos = new ArrayList<>();
        for (String id : waiters) {
            infos.add(NettyVO.strJson2Netty(RedisUtil.getWaitingCustomerInfo(id)));
        }
        vo.setMessage(JSONArray.toJSONString(infos));

        NettyVO.sendMessage(context.channel(), vo);
    }
}
