package com.java.bank.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    @PostMapping("/send")
    public void sendNotification(@RequestBody Map<String, String> message) {
        System.out.println("🔔 Notification: " + message.get("message"));
    }
}
