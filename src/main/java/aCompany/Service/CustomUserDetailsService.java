package aCompany.Service;

import aCompany.Repository.UserRepository;
import aCompany.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CustomUserDetailsService {
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    public CustomUserDetailsService() {
    }
     @Autowired
    public CustomUserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User loadUserByUsername(String user){
        Optional<User> option = userRepository.findByUsername(user);

        if(option.isPresent()){
            return option.get();
        }else{
            throw new UsernameNotFoundException("user not found");
        }

    }
}
