package com.example.job_service.service;

import com.example.job_service.model.Job;
import com.example.job_service.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    public Job createJob(Job job) {
        // Future: Add validation, logging, or status checks here.
        job.setStatus("OPEN"); // Set initial status
        return jobRepository.save(job);
    }

    public Job findJobById(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));
    }
}
