package com.network.social_network.cli;

import com.network.social_network.dto.user.UserRegistrationDto;
import com.network.social_network.repository.UserRepository;
import com.network.social_network.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CreateAdminUserAppStartupRunner implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public CreateAdminUserAppStartupRunner(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public void run (String... args) throws Exception {
        var user = userRepository.findByUsername("admin");

        if (user == null) {
            var adminUser = new UserRegistrationDto(
                    "admin",
                    "test@test.com",
                    "password",
                    "admin",
                    "admin",
                    0
            );

            userService.register(adminUser);
        }
    }
}
