package com.network.social_network.controller;

import com.network.social_network.dto.user.UserDto;
import com.network.social_network.dto.user.UserAuthenticationDto;
import com.network.social_network.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    //Todo: change privileges name

    @PostMapping(value = "/login")
    public HashMap<String, String> login(@RequestBody UserAuthenticationDto request) {
        HashMap<String, String> response = new HashMap<>();
        response.put("token", userService.login(request.getUsername(), request.getPassword()));

        return response;
    }

    @PostMapping("/register")
    public HashMap<String, String> register(@RequestBody UserDto userDto) {
        HashMap<String, String> response = new HashMap<>();
        response.put("token", userService.register(userDto));

        return response;
    }

    @GetMapping("/verify/{token}")
    public HttpStatus verifyAccount(@PathVariable String token) {
        userService.verifyAccount(token);

        return HttpStatus.OK;
    }
}
