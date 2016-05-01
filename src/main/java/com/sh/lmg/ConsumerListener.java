package com.sh.lmg;

import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by liaomengge on 16/5/1.
 */
@Component("consumerListener")
public class ConsumerListener implements MessageListener {

    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            System.out.println("recevie message : " + textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
