package org.hx.netty.netty.utils;

import org.hx.netty.netty.constants.Constant;
import org.hx.netty.netty.constants.NettyCache;
import org.hx.netty.netty.constants.NettyCodeEnum;
import org.hx.netty.netty.constants.NettyVO;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;


/**
 * @author Upoint0002
 */
@Component
public class TopicSubscriber {

    @JmsListener(destination = Constant.SINGLE_SEND)
    private void singleSendListener(String message) throws Exception {
        NettyVO vo = NettyVO.strJson2Netty(message);
        assert vo != null;
        if (NettyCache.channelMap.containsKey(vo.getTo())) {
            NettyVO.sendMessage(NettyCache.channelMap.get(vo.getTo()), vo);
        }
    }

    @JmsListener(destination = Constant.TRAN_BROAD_CAST)
    private void broadCastListener(String message) {
        NettyCache.channelMap.values().stream()
                .filter(item -> NettyCodeEnum.STAFF_ONLINE.getCode().equals(item.attr(Constant.NettyKey.TYPE).get()))
                .forEach(item -> NettyVO.sendMessage(item, message)
                );
    }
    @JmsListener(destination = Constant.DOCKING_SUCCESS)
    private void dockSuccessListener(String message) throws Exception {
        NettyVO vo = NettyVO.strJson2Netty(message);
        assert vo != null;
        NettyCache.channelMap.values().stream()
                .filter(item -> NettyCodeEnum.STAFF_ONLINE.getCode().equals(item.attr(Constant.NettyKey.TYPE).get()) && !vo.getStaffId().equals(item.attr(Constant.NettyKey.ID).get()))
                .forEach(item -> NettyVO.sendMessage(item, vo)
                );
    }

    @JmsListener(destination = Constant.DOCK_SUCCESS_BACK)
    private void dockSuccessBackListener(String message) throws Exception {
        NettyVO vo = NettyVO.strJson2Netty(message);
        assert vo != null;
        if (NettyCache.channelMap.containsKey(vo.getTo())) {
            NettyVO.sendMessage(NettyCache.channelMap.get(vo.getTo()), vo);
            NettyCache.one2One.put(vo.getTo(), vo.getStaffId());
            RedisUtil.addOne2One(vo.getStaffId(), vo.getTo());
        }
    }
}
