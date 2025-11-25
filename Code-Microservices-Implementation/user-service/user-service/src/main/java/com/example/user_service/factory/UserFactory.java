package com.example.user_service.factory;

import com.example.user_service.model.User;

public class UserFactory {
    // 1. Singleton Pattern implementation
    private static UserFactory instance;

    private UserFactory() {}

    public static synchronized UserFactory getInstance() {
        if (instance == null) {
            instance = new UserFactory();
        }
        return instance;
    }

    // 2. Factory Method Pattern implementation
    public User createUser(String type, String name, String email) {
        if ("FREELANCER".equalsIgnoreCase(type)) {
            return User.builder().username(name).email(email).role("FREELANCER").bio("Ready to work").build();
        } else if ("CLIENT".equalsIgnoreCase(type)) {
            return User.builder().username(name).email(email).role("CLIENT").bio("Looking for talent").build();
        }
        throw new IllegalArgumentException("Unknown user type");
    }
}