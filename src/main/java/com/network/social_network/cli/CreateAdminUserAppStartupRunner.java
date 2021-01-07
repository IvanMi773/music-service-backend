package com.network.social_network.cli;

import com.network.social_network.model.User;
import com.network.social_network.repository.UserRepository;
import com.network.social_network.security.ApplicationUserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CreateAdminUserAppStartupRunner implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public CreateAdminUserAppStartupRunner (PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public void run (String... args) throws Exception {
        var user = userRepository.findByUsername("admin");

        if (user == null) {
            var adminUser = new User(
                    "test@test.com",
                    "admin",
                    passwordEncoder.encode("password"),
                    "admin",
                    "admin",
                    new Date().toInstant(),
                    new Date().toInstant(),
                    ApplicationUserRole.ADMIN.getRole()
            );

            userRepository.save(adminUser);
        }
    }
}
