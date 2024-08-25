package com.utkubayrak.paymentservice.controller;

import com.utkubayrak.paymentservice.model.NotificationMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class NotificationController {
    private final SimpMessagingTemplate messagingTemplate;

    public void sendNotification(String message) {
        NotificationMessage notificationMessage = new NotificationMessage(message);
        messagingTemplate.convertAndSend("/topic/notifications", notificationMessage);
    }
}
