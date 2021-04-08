package com.network.social_network.controller;

import com.network.social_network.dto.user.UserRegistrationDto;
import com.network.social_network.dto.user.UserLoginDto;
import com.network.social_network.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/login")
    public HashMap<String, String> login(@RequestBody UserLoginDto request) {
        try {
            HashMap<String, String> response = new HashMap<>();
            response.put("token", userService.login(request.getUsername(), request.getPassword()));

            return response;
        } catch (Exception exc) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect username or password", exc);
        }
    }

    @PostMapping("/register")
    public HashMap<String, String> register(@RequestBody UserRegistrationDto userRegistrationDto) {
        try {
            HashMap<String, String> response = new HashMap<>();
            response.put("token", userService.register(userRegistrationDto));

            return response;
        } catch (Exception exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username or email is already exists", exc);
        }
    }

//    @GetMapping("/verify/{token}")
//    public HttpStatus verifyAccount(@PathVariable String token) {
//        userService.verifyAccount(token);
//
//        return HttpStatus.OK;
//    }
}
