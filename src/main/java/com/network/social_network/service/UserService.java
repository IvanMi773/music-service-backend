package com.network.social_network.service;

import com.network.social_network.dto.user.UserDto;
import com.network.social_network.exception.CustomException;
import com.network.social_network.model.User;
import com.network.social_network.repository.UserRepository;
import com.network.social_network.security.ApplicationUserRole;
import com.network.social_network.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
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

    @Autowired
    private AuthenticationManager authenticationManager;

    public UserService (UserRepository userRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            var user = userRepository.findByUsername(username);

            return jwtTokenProvider.generateToken(username, user.getRole());
        } catch (AuthenticationException e) {
            throw new CustomException("Error with logging in", HttpStatus.BAD_REQUEST);
        }
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
                    ApplicationUserRole.STUDENT.getRole()
            );

            userRepository.save(user);
            return jwtTokenProvider.generateToken(user.getUsername(), user.getRole());
        } else {
            throw new CustomException("Username or email is already use", HttpStatus.BAD_REQUEST);
        }
    }
}
