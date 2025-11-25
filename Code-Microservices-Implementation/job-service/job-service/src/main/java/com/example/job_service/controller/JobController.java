package com.example.job_service.controller;

import com.example.job_service.model.Job;
import com.example.job_service.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    // POST http://localhost:8082/api/jobs
    @PostMapping
    public ResponseEntity<Job> createNewJob(@RequestBody Job job) {
        Job createdJob = jobService.createJob(job);
        return new ResponseEntity<>(createdJob, HttpStatus.CREATED);
    }

    // GET http://localhost:8082/api/jobs/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobDetails(@PathVariable Long id) {
        Job job = jobService.findJobById(id);
        return ResponseEntity.ok(job);
    }
}