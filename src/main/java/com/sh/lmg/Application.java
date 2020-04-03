package com.sh.lmg;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.TimeUnit;


public class Application {

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:spring-jms.xml"});
        Producer producer = context.getBean("producer", Producer.class);
        for (int i = 1; i < 1000; i++) {
            producer.sendMsg("hello world");
            TimeUnit.SECONDS.sleep(1);
        }
    }

}
