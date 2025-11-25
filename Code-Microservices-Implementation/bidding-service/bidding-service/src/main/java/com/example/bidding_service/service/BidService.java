package com.example.bidding_service.service;

import com.example.bidding_service.model.Bid;
import com.example.bidding_service.repository.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// service/BidService.java
@Service
public class BidService {
    @Autowired
    private BidRepository bidRepository;

    public Bid placeBid(Bid bid) {
        bid.setStatus("SUBMITTED");
        // Future: Validation to check if the freelancerId and jobId exist
        return bidRepository.save(bid);
    }
}