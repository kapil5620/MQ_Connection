package com.example.MQ_Connector.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

import javax.jms.Connection;

@Configuration
@EnableRetry
public class MQConfigUtil {

    static Connection beConnection;
    static Connection orgConnection;
    static Connection distConnection;

    //static block to create the connection
    static {
        System.out.println("Inside the static block");
        try {
            beConnection = MQConnectionConfig.createConnection("Customer Entity ");
            orgConnection = MQConnectionConfig.createConnection("Organization Entity");
            distConnection = MQConnectionConfig.createConnection("District Entity");
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    public static Connection getBeConnection() {
        return beConnection;
    }

    public static Connection getOrgConnection() {
        return orgConnection;
    }

    public static Connection getDistConnection() {
        return distConnection;
    }
}
