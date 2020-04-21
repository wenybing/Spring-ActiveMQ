package com.mq.demo;

import org.apache.activemq.broker.jmx.BrokerViewMBean;
import org.apache.activemq.broker.jmx.QueueViewMBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class ApplicationMonitor {
    private volatile static boolean isFull = true;
    private static final long MAX_QUEUE_SIZE = 150;

    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = new MyRunnable();
        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        thread.start();
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:spring-jms.xml"});
        Producer producer = context.getBean("producer", Producer.class);
        while (true) {
            if (!isFull) {
                System.out.println("start send message");
                producer.sendMsg("hello world");
                TimeUnit.SECONDS.sleep(1);
            } else {
                TimeUnit.SECONDS.sleep(1);
                System.out.println("the queue is full, and i wouldn't send message to the queue");
            }
        }
    }

    private static class MyRunnable implements Runnable {
        private static final String connectorPort = "1099";
        private static final String connectorPath = "/jmxrmi";
        private static final String jmxDomain = "jmx-domain";// 必须与activemq.xml中的jmxDomainName一致

        public void run() {
            JMXServiceURL url;
            ObjectName name = null;
            MBeanServerConnection connection = null;
            try {
                url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://192.168.30.181:" + connectorPort + connectorPath);
                JMXConnector connector = JMXConnectorFactory.connect(url);
                connector.connect();
                connection = connector.getMBeanServerConnection();
                name = new ObjectName(jmxDomain + ":brokerName=localhost,type=Broker");
            } catch (MalformedObjectNameException | IOException e) {
                e.printStackTrace();
            }
            BrokerViewMBean mBean = MBeanServerInvocationHandler.newProxyInstance(connection, name, BrokerViewMBean.class, true);

            while (true) {
                for (ObjectName queueName : mBean.getQueues()) {
                    QueueViewMBean queueMBean = MBeanServerInvocationHandler.newProxyInstance(connection, queueName, QueueViewMBean.class, true);

                    if ("p2p-queue3".equals(queueMBean.getName())) {
                        long size = queueMBean.getQueueSize();
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Size --- " + size);
                        if (size < MAX_QUEUE_SIZE) {
                            isFull = false;
                            break;
                        }
                    }
                }
            }
        }
    }
}
