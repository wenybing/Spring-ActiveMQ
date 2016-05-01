package com.sh.lmg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * Created by liaomengge on 16/5/1.
 */
@Component("producer")
public class Producer {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendMsg(String messgae) {
        jmsTemplate.convertAndSend(messgae);
    }

    public void sendMsg(Destination destination, final String message) {
        jmsTemplate.send(destination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                System.out.println("send message : " + message);
                return session.createTextMessage(message);
            }
        });
    }
}
