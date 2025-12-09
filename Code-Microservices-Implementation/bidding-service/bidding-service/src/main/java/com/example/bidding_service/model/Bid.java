package com.example.bidding_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bids")
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long jobId;          // Link to the Job Service
    private Long freelancerId;   // Link to the User Service
    private Double bidAmount;    // The price proposed
    private String proposalText; // Description of the proposal
    private LocalDateTime bidTime = LocalDateTime.now();
    private String status;       // e.g., "SUBMITTED", "ACCEPTED", "REJECTED"
}