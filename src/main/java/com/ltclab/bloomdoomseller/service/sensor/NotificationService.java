package com.ltclab.bloomdoomseller.service.sensor;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {


    public void sendNotification(String message, String userId) {

        System.out.println("Sending notification to user " + userId + ": " + message);
    }
}
