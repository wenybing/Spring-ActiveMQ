package com.mq.demo;

import org.apache.activemq.broker.jmx.BrokerViewMBean;
import org.apache.activemq.broker.jmx.QueueViewMBean;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;


public class StateMonitor {

    private static final String connectorPort = "1099";
    private static final String connectorPath = "/jmxrmi";
    private static final String jmxDomain = "org.apache.activemq";// 必须与activemq.xml中的jmxDomainName一致
    private static final String brokerName = "wenyb-broker";// 必须与activemq.xml中的brokerName一致

    public static void main(String[] args) throws Exception {
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:" + connectorPort + connectorPath);
        JMXConnector connector = JMXConnectorFactory.connect(url);
        connector.connect();
        MBeanServerConnection connection = connector.getMBeanServerConnection();
        ObjectName name = new ObjectName(jmxDomain + ":brokerName=" + brokerName + ",type=Broker");
        BrokerViewMBean mBean = MBeanServerInvocationHandler.newProxyInstance(connection, name, BrokerViewMBean.class, true);

        for (ObjectName queueName : mBean.getQueues()) {
            QueueViewMBean queueMBean = MBeanServerInvocationHandler.newProxyInstance(connection, queueName, QueueViewMBean.class, true);

            System.out.println("\n------------------------------\n");

            // 消息队列名称
            System.out.println("Queue Name --- " + queueMBean.getName());

            // 队列中剩余的消息数
            System.out.println("Queue Size --- " + queueMBean.getQueueSize());

            // 消费者数
            System.out.println("Number of Consumers --- " + queueMBean.getConsumerCount());

            // 出队数
            System.out.println("Number of Dequeue ---" + queueMBean.getDequeueCount());
        }
    }
}
