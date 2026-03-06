package aCompany.Service;

import aCompany.Repository.UserRepository;
import aCompany.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User register(User user){
        User newUser = new User();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        newUser.setPassword(encodedPassword);
        newUser.setUsername(String.valueOf(user.getUsername()));
        return userRepository.save(newUser);

    }
}

