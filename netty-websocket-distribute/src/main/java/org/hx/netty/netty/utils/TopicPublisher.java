package org.hx.netty.netty.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Topic;

/**
 * @author Upoint0002
 */
@Component
public class TopicPublisher {
    private static Topic singleSendTopic;

    @Autowired
    public void setSingleSendTopic(Topic singleSendTopic) {
        TopicPublisher.singleSendTopic = singleSendTopic;
    }

    private static Topic broadCastTopic;

    private static Topic dockSuccessTopic;

    private static Topic dockSuccessBackTopic;

    @Autowired
    public void setBroadCastTopic(Topic broadCastTopic) {
        TopicPublisher.broadCastTopic = broadCastTopic;
    }

    @Autowired
    public void setDockSuccessTopic(Topic dockSuccessTopic) {
        TopicPublisher.dockSuccessTopic = dockSuccessTopic;
    }

    @Autowired
    public void setDockSuccessBackTopic(Topic dockSuccessBackTopic) {
        TopicPublisher.dockSuccessBackTopic = dockSuccessBackTopic;
    }

    private static JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    public void setJmsMessagingTemplate(JmsMessagingTemplate jmsMessagingTemplate) {
        TopicPublisher.jmsMessagingTemplate = jmsMessagingTemplate;
    }


    public static void singleSend(String message) {
        jmsMessagingTemplate.convertAndSend(singleSendTopic, message);
    }

    public static void transBroadCast(String message) {
        jmsMessagingTemplate.convertAndSend(broadCastTopic, message);
    }

    public static void dockingSuccessBack(String message) {
        jmsMessagingTemplate.convertAndSend(dockSuccessBackTopic, message);
    }

    public static void dockingSuccess(String message) {
        jmsMessagingTemplate.convertAndSend(dockSuccessTopic, message);
    }
}
