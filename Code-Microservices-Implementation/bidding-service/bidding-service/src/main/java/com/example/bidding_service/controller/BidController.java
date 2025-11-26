package com.example.bidding_service.controller;

import com.example.bidding_service.model.Bid;
import com.example.bidding_service.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// controller/BidController.java
@RestController
@RequestMapping("/api/bids")
public class BidController {

    @Autowired
    private BidService bidService;

    // POST http://localhost:8083/api/bids
    @PostMapping
    public ResponseEntity<Bid> placeBid(@RequestBody Bid bid) {
        return new ResponseEntity<>(bidService.placeBid(bid), HttpStatus.CREATED);
    }
}