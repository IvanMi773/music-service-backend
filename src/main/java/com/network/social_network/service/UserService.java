package com.network.social_network.service;

import com.network.social_network.dto.user.UserDto;
import com.network.social_network.dto.user.UserLibraryDto;
import com.network.social_network.dto.user.UserProfileDto;
import com.network.social_network.dto.user.UserUpdateDto;
import com.network.social_network.exception.CustomException;
import com.network.social_network.model.*;
import com.network.social_network.repository.PlaylistRepository;
import com.network.social_network.repository.UserRepository;
//import com.network.social_network.repository.VerificationTokenRepository;
import com.network.social_network.security.jwt.JwtTokenProvider;
import com.network.social_network.service.mail.MailService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PlaylistRepository playlistRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
//    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final FileUploadService fileUploadService;

    public UserService (
            UserRepository userRepository,
            PlaylistRepository playlistRepository,
            JwtTokenProvider jwtTokenProvider,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            MailService mailService,
            FileUploadService fileUploadService) {
        this.userRepository = userRepository;
        this.playlistRepository = playlistRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
//        this.verificationTokenRepository = verificationTokenRepository;
        this.mailService = mailService;
        this.fileUploadService = fileUploadService;
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
                    userDto.getEmail(),
                    userDto.getUsername(),
                    passwordEncoder.encode(userDto.getPassword()),
                    userDto.getFirstName(),
                    userDto.getLastName(),
                    "default_user.png",
                    new Date().toInstant(),
                    null,
                    UserRole.STUDENT.getRole(),
                    false,
                    false
            );
            userRepository.save(user);

            //Todo: correct file name
            var uploadsPlaylist = new Playlist(user, "Uploads", "default.png", PlayListState.PRIVATE);
            var likedPlaylist = new Playlist(user, "Liked", "liked.png", PlayListState.PRIVATE);
            var historyPlaylist = new Playlist(user, "History", "default.png", PlayListState.PRIVATE);

            playlistRepository.save(uploadsPlaylist);
            playlistRepository.save(likedPlaylist);
            playlistRepository.save(historyPlaylist);

//            String token = generateVerificationToken(user);
//            mailService.sendMail(new VerificationMail(
//                    "Please activate your account",
//                    user.getEmail(),
//                    "localhost:8080/api/auth/verify/" + token
//            ));

            return jwtTokenProvider.generateToken(user.getUsername(), user.getRole());
        } else {
            throw new CustomException("Username or email is already use", HttpStatus.BAD_REQUEST);
        }
    }

//    private String generateVerificationToken (User user) {
//        String token = UUID.randomUUID().toString();
//
//        VerificationToken verificationToken = new VerificationToken(
//                token,
//                user,
//                Instant.now().plusSeconds(10800) // 3 hours
//        );
//
//        verificationTokenRepository.save(verificationToken);
//
//        return token;
//    }

//    public void verifyAccount (String token) {
//        //Todo: check if token expire
//        VerificationToken verificationToken = verificationTokenRepository.findByToken(token).get();
//
//        String username = verificationToken.getUser().getUsername();
//        User user = userRepository.findByUsername(username);
//        user.setIsEnabled(true);
//        user.setEnabled_at(Instant.now());
//        userRepository.save(user);
//
//        verificationTokenRepository.deleteById(verificationToken.getId());
//    }

    public UserProfileDto changeSubscription (User channel, User subscriber) {

        Set<User> subscribers = channel.getSubscribers();

        if (subscribers.contains(subscriber)) {
            subscribers.remove(subscriber);
        } else {
            subscribers.add(subscriber);
        }

        userRepository.save(channel);

        var userProfileDto = new UserProfileDto(
                channel.getId(),
                channel.getUsername(),
                channel.getFirstName(),
                channel.getLastName(),
                channel.getSubscriptions().size(),
                channel.getSubscribers().size(),
                channel.getPlaylists().get(0).getSongs().size(),
                channel.getEmail(),
                channel.getProfilePhoto()
        );

        return userProfileDto;
    }

    public UserProfileDto getUserProfileDtoByUsername (String username) {
        var user = userRepository.findByUsername(username);
        var userProfileDto = new UserProfileDto(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getSubscriptions().size(),
                user.getSubscribers().size(),
                user.getPlaylists().get(0).getSongs().size(),
                user.getEmail(),
                user.getProfilePhoto()
        );

        return userProfileDto;
    }

    public UserLibraryDto getUserByUsername (String username) {
        //Todo: create enum 'playlist name' (history, liked, uploads)
        var user = userRepository.findByUsername(username);
        var subscribers = new HashSet<UserProfileDto>();
        var subscriptions = new HashSet<UserProfileDto>();

        for (User u : user.getSubscribers()) {
            subscribers.add(new UserProfileDto(
                    u.getId(),
                    u.getUsername(),
                    u.getFirstName(),
                    u.getLastName(),
                    u.getSubscriptions().size(),
                    u.getSubscribers().size(),
                    u.getPlaylists().get(0).getSongs().size(),
                    u.getEmail(),
                    u.getProfilePhoto()
            ));
        }

        for (User u : user.getSubscriptions()) {
            subscriptions.add(new UserProfileDto(
                    u.getId(),
                    u.getUsername(),
                    u.getFirstName(),
                    u.getLastName(),
                    u.getSubscriptions().size(),
                    u.getSubscribers().size(),
                    u.getPlaylists().get(0).getSongs().size(),
                    u.getEmail(),
                    u.getProfilePhoto()
            ));
        }

        return new UserLibraryDto(subscribers, subscriptions);
    }

    public void updateUser (String username, UserUpdateDto user) {
        var userToUpdate = userRepository.findByUsername(username);

        if (!userToUpdate.getProfilePhoto().equals(user.getAvatar().getOriginalFilename())) {
            userToUpdate.setProfilePhoto(fileUploadService.saveAvatars(user.getAvatar()));
        }

        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());

        userRepository.save(userToUpdate);
    }

    public void delete (String username) {
        userRepository.deleteByUsername(username);
    }
}
