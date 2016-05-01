package com.sh.lmg;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.jms.Destination;

/**
 * Created by liaomengge on 16/5/1.
 */
public class Application {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:spring-jms.xml"});
        Destination destination = (Destination) context.getBean("queueDestination");
        Producer producer = context.getBean("producer", Producer.class);
        for (int i = 0; i < 10; i++) {
            producer.sendMsg(destination, "hello, world");
        }
    }
}
