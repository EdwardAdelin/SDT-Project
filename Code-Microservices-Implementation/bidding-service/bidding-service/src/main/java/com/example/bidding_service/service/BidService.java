package com.example.bidding_service.service;

import com.example.bidding_service.client.JobClient;
import com.example.bidding_service.client.UserClient;
import com.example.bidding_service.config.MessagingConfig;
import com.example.bidding_service.dto.JobDTO;
import com.example.bidding_service.dto.UserDTO;
import com.example.bidding_service.model.Bid;
import com.example.bidding_service.repository.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BidService {
    @Autowired private BidRepository bidRepository;
    @Autowired private UserClient userClient;
    @Autowired private JobClient jobClient;
    @Autowired private RabbitTemplate rabbitTemplate;  // <-- ADD THIS

    public Bid placeBid(Bid bid) {
        // 1. Validate Freelancer (Call User Service)
        UserDTO user = userClient.getUserById(bid.getFreelancerId());
        if (!"FREELANCER".equalsIgnoreCase(user.getRole())) {
            throw new RuntimeException("Only FREELANCERS can place bids!");
        }

        // 2. Validate Job (Call Job Service)
        JobDTO job = jobClient.getJobById(bid.getJobId());
        if (job == null) {
            throw new RuntimeException("Job not found!");
        }

        // 3. Save Bid
        bid.setBidTime(LocalDateTime.now());
        Bid savedBid = bidRepository.save(bid);

        // 4. SEND ASYNCHRONOUS NOTIFICATION (Decoupled Action)
        String notificationMessage = String.format(
                "Bid %d placed by Freelancer %d on Job %d.",
                savedBid.getId(), savedBid.getFreelancerId(), savedBid.getJobId()
        );

        // Publish the message to the Exchange
        rabbitTemplate.convertAndSend(
                MessagingConfig.EXCHANGE_NAME,
                MessagingConfig.ROUTING_KEY_BID_PLACED,
                notificationMessage
        );

        System.out.println("--- ASYNC MESSAGE SENT: " + notificationMessage);

        return savedBid;

    }
}