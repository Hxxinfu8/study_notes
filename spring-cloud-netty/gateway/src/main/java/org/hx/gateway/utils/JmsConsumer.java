package org.hx.gateway.utils;

import com.alibaba.fastjson.JSONArray;
import org.hx.gateway.ConsistentHashUtil;
import org.hx.gateway.dto.ConsistentHashCircleDTO;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * mq消息消费者
 * @author hx
 */
@Component
public class JmsConsumer {
    @JmsListener(destination = "ConsistentHashCircle")
    private void getConsistentHashCircle(String message) {
        ConsistentHashCircleDTO circleDTO = JSONArray.parseObject(message, ConsistentHashCircleDTO.class);
        ConsistentHashUtil.realNodes = circleDTO.realNodes;
        ConsistentHashUtil.virtualNodes = circleDTO.virtualNodes;
    }
}
