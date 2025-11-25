package com.example.job_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "jobs")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Double budget;
    private String status; // e.g., "OPEN", "IN_PROGRESS", "CLOSED"

    // Links to the client who posted the job (from User Service)
    private Long clientId;
}
