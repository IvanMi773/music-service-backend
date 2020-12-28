package com.network.social_network.controller.auth;

import com.network.social_network.dto.UserDto;
import com.network.social_network.security.jwt.UsernameAndPasswordAuthenticationRequest;
import com.network.social_network.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody UsernameAndPasswordAuthenticationRequest request) {
        return userService.login(request.getUsername(), request.getPassword());
    }

    @PostMapping("/register")
    public String register(@RequestBody UserDto userDto) {
        return userService.register(userDto);
    }
}
