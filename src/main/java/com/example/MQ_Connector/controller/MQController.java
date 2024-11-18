package com.example.MQ_Connector.controller;

import com.example.MQ_Connector.service.MQService;
import com.example.MQ_Connector.util.MQConfigUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.*;

@RestController
@RequestMapping("/api/ibm/mq")
@EnableScheduling
public class MQController {

    @Autowired
    MQService mqService;

    int beCount = 1;
    int orgCount = 1;
    int distCount = 1;

    //    @Scheduled(cron = "* * * * * *")
    @PostMapping("/scheduledBePublishMessage")
    public String scheduledBePublishMessage() {

        String message = "Hello MQ. I am the " + beCount + " be message";
        try {
            //retrieve the active connection.
            Connection connection = MQConfigUtil.getBeConnection();

            //create the session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Queue queue = session.createQueue("DOWNSTREAM.QUEUE.ONE");

            //create the producer
            MessageProducer producer = session.createProducer(queue);
            TextMessage textMessage = session.createTextMessage(message);

            // send the message through producer
            producer.send(textMessage);
            System.out.println("Message: " + message + "Published Successfully to the Queue");
            beCount++;

            System.out.println("BE Connection Status: " + connection + "\n" + session);
            //closing the producer and sessions
            producer.close();
            session.close();

            return "Message: " + message + "Published Successfully to the Queue";
        } catch (JMSException exception) {
            throw new RuntimeException("Failed to send the message to the Queue: " + exception.getMessage());
        }
    }

    //    @Scheduled(cron = "* * * * * *")
    @PostMapping("/scheduledOrgPublishMessage")
    public String scheduledOrgPublishMessage() {

        String message = "Hello MQ. I am the " + orgCount + " organization message";
        try {
            //retrieve the active connection.
            Connection connection = MQConfigUtil.getOrgConnection();

            //create the session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Queue queue = session.createQueue("DOWNSTREAM.QUEUE.TWO");

            //create the producer
            MessageProducer producer = session.createProducer(queue);
            TextMessage textMessage = session.createTextMessage(message);

            System.out.println("ORG Connection Status: " + connection + "\n" + session);

            // send the message through producer
            producer.send(textMessage);
            System.out.println("Message: " + message + "Published Successfully to the Queue");
            orgCount++;

            //closing the producer and sessions
            producer.close();
            session.close();

            return "Message: " + message + "Published Successfully to the Queue";
        } catch (JMSException exception) {
            throw new RuntimeException("Failed to send the message to the Queue: " + exception.getMessage());
        }
    }

    //    @Scheduled(cron = "* * * * * *")
    @PostMapping("/scheduledDistPublishMessage")
    public String scheduledDistPublishMessage() {

        String message = "Hello MQ. I am the " + distCount + " district message";
        try {
            //retrieve the active connection.
            Connection connection = MQConfigUtil.getDistConnection();

            //create the session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Queue queue = session.createQueue("DOWNSTREAM.QUEUE.THREE");

            //create the producer
            MessageProducer producer = session.createProducer(queue);
            TextMessage textMessage = session.createTextMessage(message);
            System.out.println("DIST Connection Status: " + connection + "\n" + session);

            // send the message through producer
            producer.send(textMessage);
            System.out.println("Message: " + message + "Published Successfully to the Queue");
            distCount++;

            //closing the producer and sessions
            producer.close();
            session.close();

            return "Message: " + message + " Published Successfully to the Queue";
        } catch (JMSException exception) {
            throw new RuntimeException("Failed to send the message to the Queue: " + exception.getMessage());
        }
    }

    @PostMapping("/publishMessage")
    public String publishMessageToQueue(String message) {
        try {
            //retrieve the active connection.
            Connection connection = MQConfigUtil.getBeConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            //create the session
            Queue queue = session.createQueue("DOWNSTREAM.QUEUE.TWO");

            //create the producer
            MessageProducer producer = session.createProducer(queue);
            TextMessage textMessage = session.createTextMessage(message);

            // send the message through producer
            producer.send(textMessage);
            System.out.println("Message: " + message + " Published Successfully to the Queue");

            //closing the producer and sessions
            producer.close();
            session.close();

            return "Message: " + message + " Published Successfully to the Queue";

        } catch (JMSException exception) {
            System.err.println("Retrying due to exception: " + exception.getMessage());
            throw new RuntimeException("Failed to send the message to the Queue: " + exception.getMessage());
        }
    }
}
