package com.mq.demo;

import org.springframework.stereotype.Component;

import javax.jms.*;
import java.util.concurrent.TimeUnit;


@Component("consumerListener")
public class ConsumerListener implements MessageListener {

    public void onMessage(Message message) {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            try {
                System.out.println("receive message : " + textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        } else if (message instanceof ObjectMessage) {
            ObjectMessage objectMessage = (ObjectMessage) message;
            try {
                System.out.println("receive message : " + objectMessage.getObject().toString());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
