package com.example.notification_service.listner;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.example.notification_service.config.MessagingConfig;

@Component
public class NotificationListener {

    // This method is called whenever a message arrives on the defined queue
    @RabbitListener(queues = MessagingConfig.NOTIFICATION_QUEUE)
    public void receiveMessage(String message) {
        // In a real app, this would send an email or push notification.
        System.out.println("--- RECEIVED ASYNC NOTIFICATION ---");
        System.out.println("Message: " + message);
        System.out.println("------------------------------------");
    }
}