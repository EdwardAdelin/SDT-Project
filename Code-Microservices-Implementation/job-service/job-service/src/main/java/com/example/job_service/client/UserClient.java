package com.example.job_service.client;

import com.example.job_service.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// "USER-SERVICE" matches the name registered in Eureka
@FeignClient(name = "USER-SERVICE")
public interface UserClient {

    @GetMapping("/api/users/{id}")
    UserDTO getUserById(@PathVariable("id") Long id);
}