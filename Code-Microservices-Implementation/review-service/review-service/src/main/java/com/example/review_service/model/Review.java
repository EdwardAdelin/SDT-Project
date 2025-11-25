package com.example.review_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long jobId;          // The job this review is associated with
    private Long reviewerId;     // The user who wrote the review (Client/Freelancer)
    private Long reviewedUserId; // The user being reviewed (Freelancer/Client)

    private Integer rating;      // 1 to 5 stars
    private String comment;      // Textual feedback
    private LocalDateTime reviewDate = LocalDateTime.now();
}