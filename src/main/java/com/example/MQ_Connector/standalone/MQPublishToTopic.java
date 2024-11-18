package com.example.MQ_Connector.standalone;

import com.ibm.mq.jms.MQConnectionFactory;

import javax.jms.*;

public class MQPublishToTopic {
    public static void main(String[] args) {
        MQConnectionFactory factory = new MQConnectionFactory();
        try {
            // Configure the connection factory
//            factory.setTransportType(WMQConstants.WMQ_CM_CLIENT);
            factory.setQueueManager("QM1");

            // Create JMS objects
            Connection connection = factory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Topic topic = session.createTopic("MY.TOPIC");
            MessageProducer producer = session.createProducer(topic);

            // Start the connection
            connection.start();

            // Create and send a message
            TextMessage message = session.createTextMessage("Hello, this is a test message.");
            producer.send(message);

            System.out.println("Message published to topic.");

            // Clean up
            producer.close();
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
