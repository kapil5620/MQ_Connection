package com.example.MQ_Connector.util;

import com.ibm.mq.jms.MQQueueConnectionFactory;

import javax.jms.Connection;
import javax.jms.JMSException;


public class MQConnectionConfig {

    private static Connection connection;

    public static Connection createConnection(String entity) {
        try {
            System.out.println("Initializing the connection for the "+ entity);
            //Adding the connection credentials to connect MQ
            MQQueueConnectionFactory factory = new MQQueueConnectionFactory();
            factory.setQueueManager("QM1");

            //Create Connection
            connection = factory.createConnection();

            //Start Connection
            connection.start();
            System.out.println("Local MQ Connection created successfully for the entity - "+entity);
        } catch (JMSException e) {
            throw new RuntimeException("Exception is thrown when creating the connection: " + e.getMessage());
        }
        return connection;
    }
}