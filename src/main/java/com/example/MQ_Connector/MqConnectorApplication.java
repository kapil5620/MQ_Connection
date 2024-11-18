package com.example.MQ_Connector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class MqConnectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(MqConnectorApplication.class, args);
    }
}