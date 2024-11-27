package com.ltclab.bloomdoomseller.controller.sensor;

import org.springframework.messaging.simp.SimpMessagingTemplate;

public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendMessageToClient(String message, String destination) {
        messagingTemplate.convertAndSend(destination, message);
    }
}
