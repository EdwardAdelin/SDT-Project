package com.example.user_service.service;

import com.example.user_service.factory.UserFactory;
import com.example.user_service.model.User;
import com.example.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationFacade {

    @Autowired
    private UserRepository userRepository; // Standard JPA Repository

    // The Controller calls this simple method, ignoring the complex creation logic
    public User registerUser(String type, String name, String email) {
        // Use Singleton Factory to create object
        User newUser = UserFactory.getInstance().createUser(type, name, email);

        // Save to DB
        return userRepository.save(newUser);
    }
}