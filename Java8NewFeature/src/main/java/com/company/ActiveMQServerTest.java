package com.company;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.net.URI;

public class ActiveMQServerTest {
    public static void main(String[] args) {
        Connection connection = null;
        Session session;
        Destination destination;
        MessageProducer messageProducer;
        ConnectionFactory factory = new ActiveMQConnectionFactory("admin", "esbot201", URI.create("tcp://192.168.99.218:61616"));
        try {
            ((ActiveMQConnectionFactory) factory).setTrustAllPackages(true);
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            //destination = session.createQueue("test");
            Topic topic = session.createTopic("test_topic");
            //messageProducer = session.createProducer(destination);
            MessageProducer topProducer = session.createProducer(topic);
            //messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
            topProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
            for (int i = 0 ; i < 5 ; i++) {
                TextMessage message = session.createTextMessage("你好啊" + i);
                System.out.println(message.getText());
                //messageProducer.send(message);
                topProducer.send(message);
            }
            Task task = new Task(Status.OPEN, 1);
            ObjectMessage objectMessage = session.createObjectMessage(task);
            topProducer.send(objectMessage);
            //messageProducer.send(objectMessage);
            session.commit();
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
