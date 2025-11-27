package com.example.job_service.service;

import com.example.job_service.client.UserClient;
import com.example.job_service.dto.UserDTO;
import com.example.job_service.model.Job;
import com.example.job_service.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserClient userClient; // Our Feign Client

    public Job createJob(Job job) {
        // 1. CALL USER SERVICE: Does this user exist?
        UserDTO user = userClient.getUserById(job.getClientId());

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // 2. VALIDATE ROLE: Is this user allowed to post jobs?
        if (!"CLIENT".equalsIgnoreCase(user.getRole())) {
            throw new RuntimeException("Only users with role CLIENT can post jobs!");
        }

        // 3. SAVE JOB
        job.setStatus("OPEN");
        return jobRepository.save(job);
    }

    public Job findJobById(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public Job getJobById(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found with id: " + id));
    }
}
