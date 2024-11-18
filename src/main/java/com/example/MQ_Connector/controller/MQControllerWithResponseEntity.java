package com.example.MQ_Connector.controller;

import com.example.MQ_Connector.service.MQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/ibm/mqResponseEntity")
@EnableScheduling
public class MQControllerWithResponseEntity {

    @Autowired
    MQService mqService;

    @PostMapping("/publishMessageToQueueThroughAPI")
    public Object publishMessageToQueueThroughAPI(@RequestParam String message) {
        try {
            Map<String, String> result = mqService.publishMessageToQueue(message);

            // Check status and return appropriate response.
            if ("200".equals(result.get("status"))) {
                return ResponseEntity.ok(result.get("message"));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
            }
        } catch (Exception exception) {
            System.err.println("Exception caught in controller: " + exception.getMessage());
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to send the message to the Queue: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
