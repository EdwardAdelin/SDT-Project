package com.example.review_service.controller;

import com.example.review_service.model.Review;
import com.example.review_service.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// controller/ReviewController.java
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    // POST http://localhost:8084/api/reviews
    @PostMapping
    public ResponseEntity<Review> submitReview(@RequestBody Review review) {
        Review submittedReview = reviewService.submitReview(review);
        return new ResponseEntity<>(submittedReview, HttpStatus.CREATED);
    }
}
