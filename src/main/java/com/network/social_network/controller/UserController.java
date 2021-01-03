package com.network.social_network.controller;

import com.network.social_network.model.User;
import com.network.social_network.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @PostMapping
//    public HttpStatus createUser(@RequestBody User user) {
//        userService.save(user);
//
//        return HttpStatus.OK;
//    }
}
