package com.example.bidding_service.client;

import com.example.bidding_service.dto.JobDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "JOB-SERVICE")
public interface JobClient {
    @GetMapping("/api/jobs/{id}")
    JobDTO getJobById(@PathVariable("id") Long id);
}