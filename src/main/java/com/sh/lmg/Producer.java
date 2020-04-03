package com.sh.lmg;

import org.apache.activemq.ScheduledMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.*;


@Component("producer")
public class Producer {

    private final long ONE_MIN = 30 * 1000;

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendMsg(String messgae) {
        jmsTemplate.convertAndSend(messgae);
    }

    public void sendMsg(Destination destination, final String message) {
        jmsTemplate.send(destination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                System.out.println("send message : " + message);
                TextMessage textMessage = session.createTextMessage(message);
                textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, ONE_MIN);
                return textMessage;
            }
        });
    }
}
