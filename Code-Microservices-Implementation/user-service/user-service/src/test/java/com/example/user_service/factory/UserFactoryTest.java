package com.example.user_service.factory;

import com.example.user_service.model.Role;
import com.example.user_service.model.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserFactoryTest {

    @Test
    void testCreateClient() {
        User user = UserFactory.getInstance().createUser("CLIENT", "John Doe", "john@test.com");

        assertNotNull(user);
        assertEquals(Role.CLIENT, user.getRole());
        assertEquals("John Doe", user.getUsername());
        assertEquals("Looking for talent", user.getBio());
    }

    @Test
    void testCreateFreelancer() {
        User user = UserFactory.getInstance().createUser("FREELANCER", "Jane Dev", "jane@test.com");

        assertNotNull(user);
        assertEquals(Role.FREELANCER, user.getRole());
        assertEquals("Jane Dev", user.getUsername());
        assertEquals("Ready to work", user.getBio());
    }

    @Test
    void testSingletonInstance() {
        UserFactory instance1 = UserFactory.getInstance();
        UserFactory instance2 = UserFactory.getInstance();

        assertSame(instance1, instance2, "Factory should be a Singleton");
    }
}