package com.network.social_network.service;

import com.network.social_network.dto.UserDto;
import com.network.social_network.model.User;
import com.network.social_network.repository.UserRepository;
import com.network.social_network.security.jwt.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public UserService (UserRepository userRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(String username, String password) {
        return "ad";
    }

    public String register (UserDto userDto) {
        if (!userRepository.existsByUsername(userDto.getUsername()) && !userRepository.existsByEmail(userDto.getEmail())) {
            var user = new User(
                    //Todo: rewrite code
                    userDto.getEmail(),
                    userDto.getUsername(),
                    passwordEncoder.encode(userDto.getPassword()),
                    userDto.getFirstName(),
                    userDto.getLastName(),
                    new Date().toInstant(),
                    new Date().toInstant(),
                    "Student"
            );

            userRepository.save(user);
            return jwtTokenProvider.generateToken(user.getUsername());
        } else {
            //Todo: add new exception
//            throw new RuntimeException("Username or email is already use", HttpStatus.UNPROCESSABLE_ENTITY);
            System.out.println("Username or email is already use");
            return "";
        }
    }

    //Todo: remove this
    public void save(User user) {
        userRepository.save(user);
    }
}
