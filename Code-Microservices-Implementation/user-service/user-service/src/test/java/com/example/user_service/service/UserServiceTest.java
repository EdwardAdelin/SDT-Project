package com.example.user_service.service;


import com.example.user_service.model.Role;
import com.example.user_service.model.User;
import com.example.user_service.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional // Rolls back changes after each test
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveAndRetrieveUser() {
        User user = User.builder()
                .username("TestUser")
                .email("test@test.com")
                .role(Role.CLIENT)
                .build();

        User savedUser = userService.saveUser(user);

        assertNotNull(savedUser.getId());

        Optional<User> retrievedUser = userService.getUserById(savedUser.getId());
        assertTrue(retrievedUser.isPresent());
        assertEquals("TestUser", retrievedUser.get().getUsername());
    }
}