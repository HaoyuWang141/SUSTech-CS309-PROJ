package com.ooad.dormitory.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class RequestSender {

    private static int requestAmount = 0;
    private static final int MAX_REQUEST_AMOUNT = 1000;

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendRequest(String request) {
        requestAmount += 1;
        if (requestAmount <= MAX_REQUEST_AMOUNT) {
            try {
                System.out.println("Sending request: " + request);
                jmsTemplate.convertAndSend("requestQueue", request);
            } catch (Exception e) {
                System.out.println("Error sending request: " + e.getMessage());
            }
        }
        requestAmount -= 1;
    }
}