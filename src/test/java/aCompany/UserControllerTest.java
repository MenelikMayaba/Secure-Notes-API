package aCompany;

import aCompany.Controller.UserController;
import aCompany.Service.UserService;
import aCompany.entity.Roles;
import aCompany.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

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

        ResponseEntity<User> response = userController.register(user);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("yaba", response.getBody().getUsername());
        assertEquals("encodedpassword", response.getBody().getPassword());
        assertEquals(Roles.USER, response.getBody().getRole());
    }
}
