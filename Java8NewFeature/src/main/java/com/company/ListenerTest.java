package com.company;

import javax.jms.*;

public class ListenerTest implements MessageListener {
    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                System.out.println(((TextMessage) message).getText());
            }

            if (message instanceof ObjectMessage) {
                Task task = (Task)((ObjectMessage)message).getObject();
                System.out.println(task.getPoints());
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
