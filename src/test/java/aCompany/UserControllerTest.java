package aCompany;

import aCompany.Controller.UserController;
import aCompany.Service.UserService;
import aCompany.entity.Roles;
import aCompany.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerShouldReturnRegisteredUser() {
        User user = new User();
        user.setUsername("yaba");
        user.setPassword("password");

        User registeredUser = new User();
        registeredUser.setUsername("yaba");
        registeredUser.setPassword("encodedpassword");
        registeredUser.setRole(Roles.USER);

        Mockito.when(userService.register(user)).thenReturn(registeredUser);
        Mockito.when(passwordEncoder.encode("password")).thenReturn("encodedpassword");

        ResponseEntity<User> response = userController.register(user);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("yaba", response.getBody().getUsername());
        assertEquals("encodedpassword", response.getBody().getPassword());
        assertEquals(Roles.USER, response.getBody().getRole());
    }
}
