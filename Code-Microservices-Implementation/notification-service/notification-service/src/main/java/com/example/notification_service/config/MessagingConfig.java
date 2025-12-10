package com.example.notification_service.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfig {

    public static final String EXCHANGE_NAME = "skillhub.events";
    public static final String NOTIFICATION_QUEUE = "notifications.queue";
    public static final String ROUTING_KEY_BID_PLACED = "bid.placed";

    // 1. Define the Exchange (the central hub that routes messages)
    @Bean
    public TopicExchange exchange() {
        // Topic exchange allows routing based on the routing key pattern
        return new TopicExchange(EXCHANGE_NAME);
    }

    // 2. Define the Queue (where the messages wait to be consumed)
    @Bean
    public Queue notificationQueue() {
        return new Queue(NOTIFICATION_QUEUE, true); // durable: true
    }

    // 3. Bind the Queue to the Exchange using a Routing Key
    // This tells the exchange: "Send messages with the key 'bid.placed' to the notification queue."
    @Bean
    public Binding binding(Queue notificationQueue, TopicExchange exchange) {
        return BindingBuilder.bind(notificationQueue)
                .to(exchange)
                .with(ROUTING_KEY_BID_PLACED);
    }
}
