package com.example.review_service.service;

import com.example.review_service.model.Review;
import com.example.review_service.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// service/ReviewService.java
@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public Review submitReview(Review review) {
        // Future: Validation to ensure the reviewerId and reviewedUserId are valid
        return reviewRepository.save(review);
    }
}