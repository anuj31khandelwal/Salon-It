package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class WebhookService {
    private final RestTemplate restTemplate;

    @Autowired
    public WebhookService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendWebhookNotification(String url, Map<String, Object> payload) {
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, payload, String.class);
            System.out.println("Webhook response: " + response.getStatusCode());
        } catch (Exception e) {
            System.err.println("Failed to send webhook: " + e.getMessage());
        }
    }
}

