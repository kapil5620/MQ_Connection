package com.example.MQ_Connector.service;

import com.example.MQ_Connector.util.MQConfigUtil;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.util.HashMap;
import java.util.Map;

@Service
@EnableRetry
public class MQService {

    /*    @Retryable(
                value = {JMSException.class},
                maxAttempts = 3,
                backoff = @Backoff(delay = 2000) // Optional: delay between retries (2 seconds here)
        )*/

    public Map<String, String> publishMessageToQueue(String message) {
        Map<String, String> responseMap = new HashMap<>();
        try {
            // Retrieve the active connection.
            Connection connection = MQConfigUtil.getBeConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the session and queue.
            Queue queue = session.createQueue("DOWNSTREAM.QUEUE.TWO");

            // Create the producer and send the message.
            MessageProducer producer = session.createProducer(queue);
            TextMessage textMessage = session.createTextMessage(message);
            producer.send(textMessage);

            // Close producer and session.
            producer.close();
            session.close();

            // Return a success response.
            responseMap.put("message", "Published Successfully to the Queue");
            responseMap.put("status", "200");
            return responseMap;

        } catch (JMSException exception) {
            System.err.println("Retrying due to exception: " + exception.getMessage());
            responseMap.put("message", "Failed to send the message to the Queue: " + exception.getMessage());
            responseMap.put("status", "500");
            return responseMap;
        }
    }

/*    @Recover
    public String recover(JMSException exception) {
        System.out.println("Max retry attempts reached. Re-establishing connection for Customer Entity.");
        MQConfigUtil.beConnection = MQConnectionConfig.createConnection("Customer Entity");
        return "Connection re-established after failure.";
    }*/
}
