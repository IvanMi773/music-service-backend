package com.network.social_network.service;

import com.network.social_network.dto.user.UserDto;
import com.network.social_network.exception.CustomException;
import com.network.social_network.model.*;
import com.network.social_network.repository.PlaylistRepository;
import com.network.social_network.repository.UserRepository;
import com.network.social_network.repository.VerificationTokenRepository;
import com.network.social_network.security.jwt.JwtTokenProvider;
import com.network.social_network.service.mail.MailService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PlaylistRepository playlistRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;

    public UserService (UserRepository userRepository, PlaylistRepository playlistRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, VerificationTokenRepository verificationTokenRepository, MailService mailService) {
        this.userRepository = userRepository;
        this.playlistRepository = playlistRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.verificationTokenRepository = verificationTokenRepository;
        this.mailService = mailService;
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
                    //Todo: rewrite code
                    userDto.getEmail(),
                    userDto.getUsername(),
                    passwordEncoder.encode(userDto.getPassword()),
                    userDto.getFirstName(),
                    userDto.getLastName(),
                    new Date().toInstant(),
                    null,
                    UserRole.STUDENT.getRole(),
                    false,
                    false
            );
            userRepository.save(user);

            //Todo: correct file name
            var playlist = new Playlist(user, "Uploads", 0.0, new PhotoFile("asdf", ".png"), PlayListState.PRIVATE);
            playlistRepository.save(playlist);

            String token = generateVerificationToken(user);
            mailService.sendMail(new VerificationMail(
                    "Please activate your account",
                    user.getEmail(),
                    "localhost:8080/api/auth/verify/" + token
            ));

            return jwtTokenProvider.generateToken(user.getUsername(), user.getRole());
        } else {
            throw new CustomException("Username or email is already use", HttpStatus.BAD_REQUEST);
        }
    }

    private String generateVerificationToken (User user) {
        String token = UUID.randomUUID().toString();

        VerificationToken verificationToken = new VerificationToken(
                token,
                user,
                Instant.now().plusSeconds(10800) // 3 hours
        );

        verificationTokenRepository.save(verificationToken);

        return token;
    }

    public void verifyAccount (String token) {
        //Todo: check if token expire
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token).get();

        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username);
        user.setIsEnabled(true);
        user.setEnabled_at(Instant.now());
        userRepository.save(user);

        verificationTokenRepository.deleteById(verificationToken.getId());
    }
}
