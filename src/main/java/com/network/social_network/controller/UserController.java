package com.network.social_network.controller;

import com.network.social_network.dto.user.UserProfileDto;
import com.network.social_network.repository.UserRepository;
import com.network.social_network.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController (UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/{username}")
    public UserProfileDto getProfileByUsername (@PathVariable("username") String username) {
        return userService.getUserProfileDtoByUsername(username);
    }

    @PostMapping("/change-subscription/{channelName}")
    public UserProfileDto changeSubscription (
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User userdetails,
            @PathVariable("channelName") String channelName
    ) {
        var subscriber = userRepository.findByUsername(userdetails.getUsername());
        var channel = userRepository.findByUsername(channelName);

        return userService.changeSubscription(channel, subscriber);
    }
}
