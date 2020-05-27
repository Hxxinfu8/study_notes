package org.hx.netty.netty.utils;

import org.hx.netty.netty.constants.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Upoint0002
 */
@Component
public class TopicPublisher {
    private static JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    public void setJmsMessagingTemplate(JmsMessagingTemplate jmsMessagingTemplate) {
        TopicPublisher.jmsMessagingTemplate = jmsMessagingTemplate;
    }

    public static void singleSend(String message) {
        jmsMessagingTemplate.convertAndSend(Constant.SINGLE_SEND, message);
    }

    public static void transBroadCast(String message) {
        jmsMessagingTemplate.convertAndSend(Constant.TRAN_BROAD_CAST, message);
    }

    public static void dockingSuccess(String message) {
        jmsMessagingTemplate.convertAndSend(Constant.DOCKING_SUCCESS, message);
    }
}
