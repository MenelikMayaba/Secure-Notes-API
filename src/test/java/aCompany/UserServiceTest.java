package aCompany;

import static org.junit.jupiter.api.Assertions.*;

import aCompany.Service.UserService;
import aCompany.entity.Roles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.context.support.BeanDefinitionDsl;
import org.springframework.security.crypto.password.PasswordEncoder;
import aCompany.Repository.UserRepository;
import aCompany.entity.User;

import javax.management.relation.Role;

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

        userService.register((user));

        assertEquals(Roles.USER, user.getRole());
    }
}