package com.example.bidding_service.repository;

import com.example.bidding_service.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

// repository/BidRepository.java
public interface BidRepository extends JpaRepository<Bid, Long> {}