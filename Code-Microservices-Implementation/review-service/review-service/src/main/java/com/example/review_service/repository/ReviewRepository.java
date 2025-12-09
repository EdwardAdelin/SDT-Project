package com.example.review_service.repository;

import com.example.review_service.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// repository/ReviewRepository.java
public interface ReviewRepository extends JpaRepository<Review, Long> {
    // Find all reviews for a specific user
    List<Review> findByReviewedUserId(Long reviewedUserId);
}