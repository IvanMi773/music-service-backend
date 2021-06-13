package com.network.social_network.service;

import com.network.social_network.dto.user.UserRegistrationDto;
import com.network.social_network.dto.user.UserProfileDto;
import com.network.social_network.dto.user.UserUpdateDto;
import com.network.social_network.exception.CustomException;
import com.network.social_network.mapper.UserMapper;
import com.network.social_network.model.*;
import com.network.social_network.repository.PlaylistRepository;
import com.network.social_network.repository.UserRepository;
import com.network.social_network.jwt.JwtTokenProvider;
import com.network.social_network.service.search.UserSearchService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PlaylistRepository playlistRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final FileUploadService fileUploadService;
    private final PlaylistService playlistService;
    private final UserMapper userMapper;

    public UserService (
            UserRepository userRepository,
            PlaylistRepository playlistRepository,
            JwtTokenProvider jwtTokenProvider,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            FileUploadService fileUploadService,
            PlaylistService playlistService,
            UserMapper userMapper
    ) {
        this.userRepository = userRepository;
        this.playlistRepository = playlistRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.fileUploadService = fileUploadService;
        this.playlistService = playlistService;
        this.userMapper = userMapper;
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

    public String register (UserRegistrationDto userRegistrationDto) {
        if (
                !userRepository.existsByUsername(userRegistrationDto.getUsername()) &&
                !userRepository.existsByEmail(userRegistrationDto.getEmail())
        ) {
            var user = userMapper.userRegistrationDtoToUser(userRegistrationDto, passwordEncoder);
            userRepository.save(user);

            var uploadsPlaylist = new Playlist(user, "Uploads", "default.png", PlayListState.TECHNICAL, false);
            var likedPlaylist = new Playlist(user, "Liked", "liked.png", PlayListState.TECHNICAL, false);
            var historyPlaylist = new Playlist(user, "History", "default.png", PlayListState.TECHNICAL, false);

            playlistRepository.save(uploadsPlaylist);
            playlistRepository.save(likedPlaylist);
            playlistRepository.save(historyPlaylist);

            return jwtTokenProvider.generateToken(user.getUsername(), user.getRole());
        } else {
            throw new CustomException("Username or email is already in use", HttpStatus.BAD_REQUEST);
        }
    }

    public UserProfileDto changeSubscription (User channel, User subscriber) {

        Set<User> subscribers = channel.getSubscribers();

        if (subscribers.contains(subscriber)) {
            subscribers.remove(subscriber);
        } else {
            subscribers.add(subscriber);
        }

        userRepository.save(channel);
        userRepository.save(subscriber);

        return userMapper.userToUserProfileDto(channel);
    }

    public UserProfileDto getUserProfileDtoByUsername (String username) {
        var user = userRepository.findByUsername(username);

        return userMapper.userToUserProfileDto(user);
    }

    public void updateUser (String username, UserUpdateDto user) {
        //Todo: create enum 'playlist name' (history, liked, uploads)
        var userToUpdate = userRepository.findByUsername(username);

        if (!userToUpdate.getAvatar().equals(user.getAvatar().getOriginalFilename())) {
            userToUpdate.setAvatar(fileUploadService.saveAvatars(user.getAvatar()));
        }

        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());

        userRepository.save(userToUpdate);
    }

    public void delete (String username) {
        var user = userRepository.findByUsername(username);
        for (var playlist: user.getPlaylists()) {
            playlistService.deletePlaylistById(playlist.getId());
        }
        user.setIsDeleted(true);
        userRepository.save(user);
    }

    public List<UserProfileDto> getAll() {
        var usersProfiles = new ArrayList<UserProfileDto>();

        for (var user : userRepository.findAll()) {
            usersProfiles.add(userMapper.userToUserProfileDto(user));
        }

        return usersProfiles;
    }
}
