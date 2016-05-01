package com.sh.lmg;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by liaomengge on 16/5/1.
 */
public class Application {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:spring-jms.xml"});
        Producer producer = context.getBean("producer", Producer.class);
        producer.sendMsg("hello world");
    }
}
