package com.network.social_network.controller;

import com.network.social_network.dto.user.UserDto;
import com.network.social_network.dto.user.UserAuthenticationDto;
import com.network.social_network.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody UserAuthenticationDto request) {
        return userService.login(request.getUsername(), request.getPassword());
    }

    @PostMapping("/register")
    public String register(@RequestBody UserDto userDto) {
        return userService.register(userDto);
    }

    @GetMapping("/verify/{token}")
    public HttpStatus verifyAccount(@PathVariable String token) {
        userService.verifyAccount(token);

        return HttpStatus.OK;
    }
}
