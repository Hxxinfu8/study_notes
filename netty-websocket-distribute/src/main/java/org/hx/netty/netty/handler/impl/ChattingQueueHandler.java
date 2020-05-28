package org.hx.netty.netty.handler.impl;

import com.alibaba.fastjson.JSONArray;
import io.netty.channel.ChannelHandlerContext;
import org.hx.netty.netty.constants.Constant;
import org.hx.netty.netty.constants.NettyVO;
import org.hx.netty.netty.handler.IEventHandler;
import org.hx.netty.netty.utils.RedisUtil;

/**
 * @author Upoint0002
 */
public class ChattingQueueHandler implements IEventHandler {
    @Override
    public void handle(ChannelHandlerContext context, NettyVO vo) {
        String id = context.channel().attr(Constant.NettyKey.ID).get();
        vo.setMessage(JSONArray.toJSONString(RedisUtil.getOne2One(id)));
        NettyVO.sendMessage(context.channel(), vo);
    }
}
