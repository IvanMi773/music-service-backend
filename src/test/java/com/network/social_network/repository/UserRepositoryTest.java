package com.network.social_network.repository;

import com.network.social_network.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void existsByUsername () {
        var user = new User(
                "testasdf@test.com",
                "test",
                "password",
                "asdf",
                "asdf",
                "asdfds",
                "USER",
                false
        );

        userRepository.save(user);
        assertTrue(userRepository.existsByUsername("test"));
        assertFalse(userRepository.existsByUsername("testfd"));
        userRepository.deleteByUsername("test");
    }

    @Test
    void existsByEmail () {
        var user = new User(
                "testasdf@test.com",
                "test",
                "password",
                "asdf",
                "asdf",
                "asdfds",
                "USER",
                false
        );

        userRepository.save(user);
        assertTrue(userRepository.existsByEmail("testasdf@test.com"));
        assertFalse(userRepository.existsByEmail("fjkdlsdkjsl@test.com"));
        userRepository.deleteByUsername("test");
    }

    @Test
    void findByUsername () {
        var user = new User(
                "testasdf@test.com",
                "test",
                "password",
                "asdf",
                "asdf",
                "asdfds",
                "USER",
                false
        );

        userRepository.save(user);
        assertThat(userRepository.findByUsername("test")).isNotNull();
        userRepository.deleteByUsername("test");
    }

    @Test
    void deleteByUsername () {
        var user = new User(
                "testasdf@test.com",
                "test",
                "password",
                "asdf",
                "asdf",
                "asdfds",
                "USER",
                false
        );

        userRepository.save(user);
        userRepository.deleteByUsername("test");
        assertThat(userRepository.findByUsername("test")).isNull();
    }
}