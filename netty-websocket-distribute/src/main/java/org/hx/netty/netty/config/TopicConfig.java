package org.hx.netty.netty.config;

import org.apache.activemq.command.ActiveMQTopic;
import org.hx.netty.netty.constants.Constant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

import javax.jms.Topic;

/**
 * @author Upoint0002
 */
@Configuration
@EnableJms
public class TopicConfig {
    @Bean
    public Topic singleSendTopic () {
        return new ActiveMQTopic(Constant.SINGLE_SEND);
    }

    @Bean
    public Topic broadCastTopic () {
        return new ActiveMQTopic(Constant.TRAN_BROAD_CAST);
    }

    @Bean
    public Topic dockSuccessTopic () {
        return new ActiveMQTopic(Constant.DOCKING_SUCCESS);
    }
}
