package com.example.user_service.controller;

// User Service: com.example.userservice.controller.UserController.java

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.user_service.model.User;
import com.example.user_service.service.UserRegistrationFacade;
import com.example.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // REQUIRED: Marks this class as a request handler
@RequestMapping("/api/users") // REQUIRED: Defines the base path for this service
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRegistrationFacade registrationFacade;

    // GET all users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // POST to create a user (Uses Facade + Factory + Builder)
    // URL: http://localhost:9090/user/api/users?type=CLIENT&name=John&email=john@test.com
    @PostMapping
    public ResponseEntity<User> createUser(
            @RequestParam String type,
            @RequestParam String name,
            @RequestParam String email) {

        User createdUser = registrationFacade.registerUser(type, name, email);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // GET user by ID (Used by Job Service via Feign)
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}