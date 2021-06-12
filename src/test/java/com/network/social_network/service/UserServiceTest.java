package com.network.social_network.service;

import com.network.social_network.dto.user.UserRegistrationDto;
import com.network.social_network.dto.user.UserUpdateDto;
import com.network.social_network.exception.CustomException;
import com.network.social_network.model.User;
import com.network.social_network.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void login () {
        var user = new User(
                "testasdf@test.com",
                "testasdf",
                "password",
                "asdf",
                "asdf",
                "asdfds",
                "USER",
                false
        );
        userRepository.save(user);

        assertThat(userService.login("admin", "password")).isInstanceOf(String.class);
        assertThatThrownBy(() -> userService.login("fjklsdaj", "jfadklsjlfads"))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining("Error with logging in");

        userRepository.deleteById(user.getId());
    }

    @Test
    void register () {
        var userRegistrationDto = new UserRegistrationDto(
                "testasdf",
                "testasdf@test.com",
                "password",
                "asdf",
                "asdf",
                1
        );
        var userRegistrationDto1 = new UserRegistrationDto(
                "admin",
                "testasdf@test.com",
                "password",
                "asdf",
                "asdf",
                1
        );

        assertThat(userService.register(userRegistrationDto)).isInstanceOf(String.class);
        assertThatThrownBy(() -> userService.register(userRegistrationDto1))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining("Username or email is already in use");

        userRepository.deleteByUsername(userRegistrationDto.getUsername());
        userRepository.deleteByUsername(userRegistrationDto1.getUsername());
    }

    @Test
    void changeSubscription () {
        var user = new User(
                "testasdf@test.com",
                "testasdf",
                "password",
                "asdf",
                "asdf",
                "asdfds",
                "USER",
                false
        );
        userRepository.save(user);

        var user2 = new User(
                "testajjj@test.com",
                "testajjsdf",
                "1password",
                "afsdf",
                "asfdf",
                "asdfdfs",
                "USER",
                false
        );
        userRepository.save(user2);

        userService.changeSubscription(user, user2);

        assertThat(user.getSubscribers().size()).isEqualTo(1);
//        assertThat(user2.getSubscriptions().size()).isEqualTo(1);

        userService.changeSubscription(user, user2);

        assertThat(user.getSubscribers().size()).isEqualTo(0);
        assertThat(user2.getSubscriptions().size()).isEqualTo(0);

        userRepository.deleteById(user.getId());
//        userRepository.deleteById(user2.getId());
    }

    @Test
    void getUserProfileDtoByUsername () {
    }

    @Test
    void updateUser () {
        var user = new User(
                "testasdf@test.com",
                "testasdf",
                "password",
                "asdf",
                "asdf",
                "asdfds",
                "USER",
                false
        );
        userRepository.save(user);

        var userDto = new UserUpdateDto("test@test.com", "sad", "fdasf", new MultipartFile() {
            @Override
            public String getName () {
                return null;
            }

            @Override
            public String getOriginalFilename () {
                return null;
            }

            @Override
            public String getContentType () {
                return null;
            }

            @Override
            public boolean isEmpty () {
                return false;
            }

            @Override
            public long getSize () {
                return 0;
            }

            @Override
            public byte[] getBytes () throws IOException {
                return new byte[0];
            }

            @Override
            public InputStream getInputStream () throws IOException {
                return null;
            }

            @Override
            public void transferTo (File file) throws IOException, IllegalStateException {

            }
        });

        userService.updateUser(user.getUsername(), userDto);
    }

    @Test
    void delete () {
    }

    @Test
    void getAll () {
    }
}