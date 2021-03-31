package com.network.social_network.controller;

import com.network.social_network.dto.user.UserLibraryDto;
import com.network.social_network.dto.user.UserProfileDto;
import com.network.social_network.dto.user.UserUpdateDto;
import com.network.social_network.repository.UserRepository;
import com.network.social_network.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
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

    @GetMapping("/u/{username}")
    public UserLibraryDto getUsernameById (@PathVariable("username") String username) {
        return userService.getUserByUsername(username);
    }

    @PutMapping("/update")
    public HttpStatus updateUserByUsername (@AuthenticationPrincipal User userToUpdate, @RequestBody UserUpdateDto user) {
        userService.updateUser(userToUpdate.getUsername(), user);

        return HttpStatus.OK;
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

    @DeleteMapping("/{username}")
    public HttpStatus delete (@PathVariable("username") String username) {

        userService.delete(username);

        return HttpStatus.OK;
    }
}
