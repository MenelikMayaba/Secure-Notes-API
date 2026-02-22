package com.example.securitydemo.security;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import com.example.securitydemo.repository.UserRepository;
import com.example.securitydemo.entity.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

class CustomUserDetailsServiceTest {

    @InjectMocks
    private CustomUserDetailsService userDetailsService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsernameShouldReturnUserDetails() {
        User user = new User();
        user.setUsername("yaba");
        user.setPassword("encodedpassword");
        user.setRole("USER");

        Mockito.when(userRepository.findByUsername("yaba"))
                .thenReturn(java.util.Optional.of(user));

        var userDetails = userDetailsService.loadUserByUsername("yaba");
        assertEquals("yaba", userDetails.getUsername());
        assertEquals("encodedpassword", userDetails.getPassword());
    }

    @Test
    void loadUserByUsernameShouldThrowIfNotFound() {
        Mockito.when(userRepository.findByUsername("unknown"))
                .thenReturn(java.util.Optional.empty());

        assertThrows(UsernameNotFoundException.class, () ->
                userDetailsService.loadUserByUsername("unknown")
        );
    }
}