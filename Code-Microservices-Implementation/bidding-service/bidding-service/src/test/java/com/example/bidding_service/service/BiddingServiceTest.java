package com.example.bidding_service.service;

import com.example.bidding_service.client.JobClient;
import com.example.bidding_service.client.UserClient;
import com.example.bidding_service.dto.JobDTO;
import com.example.bidding_service.dto.UserDTO;
import com.example.bidding_service.model.Bid;
import com.example.bidding_service.repository.BidRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class BidServiceTest {

    @Mock private BidRepository bidRepository;
    @Mock private UserClient userClient;
    @Mock private JobClient jobClient;
    @Mock private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private BidService bidService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPlaceBid_Success() {
        // 1. Mock Freelancer
        UserDTO freelancer = new UserDTO();
        freelancer.setId(2L);
        freelancer.setRole("FREELANCER");
        when(userClient.getUserById(2L)).thenReturn(freelancer);

        // 2. Mock Job
        JobDTO job = new JobDTO();
        job.setId(1L);
        when(jobClient.getJobById(1L)).thenReturn(job);

        // 3. Mock Save
        Bid bid = new Bid();
        bid.setFreelancerId(2L);
        bid.setJobId(1L);
        bid.setBidAmount(100.0);

        // Return the bid with an ID to simulate saving
        Bid savedBid = new Bid();
        savedBid.setId(10L);
        savedBid.setFreelancerId(2L);
        savedBid.setJobId(1L);
        savedBid.setBidAmount(100.0);

        when(bidRepository.save(any(Bid.class))).thenReturn(savedBid);

        // Execute
        Bid result = bidService.placeBid(bid);

        // Verify
        assertNotNull(result);
        assertNotNull(result.getBidTime()); // Should be set by service

        // Verify Interactions
        verify(userClient).getUserById(2L);
        verify(jobClient).getJobById(1L);
        verify(bidRepository).save(bid);
        // Verify RabbitMQ message was sent
        verify(rabbitTemplate).convertAndSend(anyString(), anyString(), anyString());
    }

    @Test
    void testPlaceBid_Failure_JobNotFound() {
        // Mock Freelancer OK
        UserDTO freelancer = new UserDTO();
        freelancer.setId(2L);
        freelancer.setRole("FREELANCER");
        when(userClient.getUserById(2L)).thenReturn(freelancer);

        // Mock Job NOT FOUND (returns null)
        when(jobClient.getJobById(99L)).thenReturn(null);

        Bid bid = new Bid();
        bid.setFreelancerId(2L);
        bid.setJobId(99L);

        // Expect Exception
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bidService.placeBid(bid);
        });

        assertEquals("Job not found!", exception.getMessage());
        verify(bidRepository, never()).save(any(Bid.class));
        verify(rabbitTemplate, never()).convertAndSend(anyString(), anyString(), anyString());
    }
}