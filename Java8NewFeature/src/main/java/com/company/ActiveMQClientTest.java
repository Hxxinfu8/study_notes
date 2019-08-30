package com.company;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;

import javax.jms.*;
import java.net.URI;

public class ActiveMQClientTest {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin", "esbot2019", URI.create("tcp://192.168.99.218:61616"));
        Connection connection;
        try {
            ((ActiveMQConnectionFactory) connectionFactory).setTrustAllPackages(true);
            connection = connectionFactory.createConnection();
            // 持久化需设置clientID
            connection.setClientID("client_1");
            connection.start();
            Session session = connection.createSession(false, ActiveMQSession.AUTO_ACKNOWLEDGE);
            //Destination destination = session.createQueue("test");
            Topic topic = session.createTopic("test_topic");
            //MessageConsumer consumer = session.createConsumer(destination);
            // 创建持久化消费者
            MessageConsumer consumer = session.createDurableSubscriber(topic, "client_1");
            consumer.setMessageListener(new ListenerTest());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
