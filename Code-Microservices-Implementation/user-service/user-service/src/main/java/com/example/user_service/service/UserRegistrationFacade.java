package com.example.user_service.service;

import com.example.user_service.factory.UserFactory;
import com.example.user_service.model.User;
import com.example.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationFacade {

    @Autowired
    private UserService userService;

    // Facade method: Hides the complexity of Factory creation + DB saving
    public User registerUser(String type, String name, String email) {
        // 1. Use Singleton Factory to create the object
        User newUser = UserFactory.getInstance().createUser(type, name, email);

        // 2. Use Service to save to DB
        return userService.saveUser(newUser);
    }
}