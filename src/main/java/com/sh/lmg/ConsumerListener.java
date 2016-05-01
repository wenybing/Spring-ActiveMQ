package com.sh.lmg;

import org.springframework.stereotype.Component;

import javax.jms.*;

/**
 * Created by liaomengge on 16/5/1.
 */
@Component("consumerListener")
public class ConsumerListener implements MessageListener {

    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            try {
                System.out.println("recevie message : " + textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        } else if (message instanceof ObjectMessage) {
            ObjectMessage objectMessage = (ObjectMessage) message;
            try {
                System.out.println("receive messgae : " + objectMessage.getObject().toString());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
