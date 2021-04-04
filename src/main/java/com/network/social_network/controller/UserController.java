package com.network.social_network.controller;

//import com.network.social_network.dto.user.UserLibraryDto;
import com.network.social_network.dto.user.UserProfileDto;
import com.network.social_network.dto.user.UserUpdateDto;
import com.network.social_network.repository.UserRepository;
import com.network.social_network.service.UserService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

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

//    @GetMapping("/u/{username}")
//    public UserLibraryDto getUserByUsername (
//            @AuthenticationPrincipal User userdetails,
//            @PathVariable("username") String username
//    ) {
//        return userService.getUserByUsername(userdetails.getUsername(), username);
//    }

    @PutMapping("/update")
    public HttpStatus updateUser (
            @AuthenticationPrincipal User userToUpdate,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("avatar") MultipartFile avatar
    ) {
        var user = new UserUpdateDto(email, firstName, lastName, avatar);
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

    @GetMapping("/avatar/{avatar}")
    public ResponseEntity getAvatar (@PathVariable String avatar) throws FileNotFoundException {
        String file = "uploads/avatars/" + avatar;

        long length = new File(file).length();

        InputStreamResource inputStreamResource = new InputStreamResource( new FileInputStream(file));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentLength(length);
        httpHeaders.setCacheControl(CacheControl.noCache().getHeaderValue());
        return new ResponseEntity(inputStreamResource, httpHeaders, HttpStatus.OK);
    }

    @DeleteMapping("/{username}")
    public HttpStatus delete (@PathVariable("username") String username) {

        userService.delete(username);

        return HttpStatus.OK;
    }
}
