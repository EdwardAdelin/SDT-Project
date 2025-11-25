package com.example.user_service.controller;

// User Service: com.example.userservice.controller.UserController.java

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // REQUIRED: Marks this class as a request handler
@RequestMapping("/api/users") // REQUIRED: Defines the base path for this service
public class UserController {

    // ... service and repository injections

    @GetMapping // REQUIRED: Defines the HTTP GET method for the base path
    public String getAllUsers() {
        return "List of users will go here"; // Placeholder response
    }
}