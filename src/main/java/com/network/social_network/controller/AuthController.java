package com.network.social_network.controller;

import com.network.social_network.dto.user.UserRegistrationDto;
import com.network.social_network.dto.user.UserLoginDto;
import com.network.social_network.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/login")
    public HashMap<String, String> login(@RequestBody UserLoginDto request) {
        HashMap<String, String> response = new HashMap<>();
        response.put("token", userService.login(request.getUsername(), request.getPassword()));

        return response;
    }

    @PostMapping("/register")
    public HashMap<String, String> register(@RequestBody UserRegistrationDto userRegistrationDto) {
        HashMap<String, String> response = new HashMap<>();
        response.put("token", userService.register(userRegistrationDto));

        return response;
    }

//    @GetMapping("/verify/{token}")
//    public HttpStatus verifyAccount(@PathVariable String token) {
//        userService.verifyAccount(token);
//
//        return HttpStatus.OK;
//    }
}
