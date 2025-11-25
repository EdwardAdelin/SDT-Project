package com.example.job_service.repository;

import com.example.job_service.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
    // Basic CRUD methods are inherited.
}
