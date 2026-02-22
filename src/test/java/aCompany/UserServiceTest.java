package aCompany;

public class UserServiceTest {
}
package com.example.securitydemo.service;

import static org.junit.jupiter.api.Assertions.*;
        import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
        import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.securitydemo.repository.UserRepository;
import com.example.securitydemo.entity.User;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerShouldEncodePasswordAndSaveUser() {
        User user = new User();
        user.setUsername("yaba");
        user.setPassword("plainpassword");

        Mockito.when(passwordEncoder.encode("plainpassword"))
                .thenReturn("encodedpassword");

        userService.register(user);

        Mockito.verify(userRepository).save(Mockito.argThat(u ->
                u.getPassword().equals("encodedpassword") &&
                        u.getUsername().equals("yaba")
        ));
    }

    @Test
    void registerShouldAssignDefaultRole() {
        User user = new User();
        user.setUsername("tester");
        user.setPassword("password");

        Mockito.when(passwordEncoder.encode("password"))
                .thenReturn("encodedpassword");

        userService.register(user);

        assertEquals("USER", user.getRole());
    }
}