package com.example.securitydemo.entity;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    void shouldSetAndGetUsername() {
        User user = new User();
        user.setUsername("yaba");
        assertEquals("yaba", user.getUsername());
    }

    @Test
    void shouldSetAndGetPassword() {
        User user = new User();
        user.setPassword("password123");
        assertEquals("password123", user.getPassword());
    }

    @Test
    void shouldHaveDefaultRoleIfNotSet() {
        User user = new User();
        user.setUsername("tester");
        // assume your User entity defaults role to "USER"
        assertEquals("USER", user.getRole());
    }

    @Test
    void shouldSetCustomRole() {
        User user = new User();
        user.setRole("ADMIN");
        assertEquals("ADMIN", user.getRole());
    }
}