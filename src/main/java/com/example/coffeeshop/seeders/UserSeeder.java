package com.example.coffeeshop.seeders;

import com.example.coffeeshop.enums.RoleEnum;
import com.example.coffeeshop.models.User;
import com.example.coffeeshop.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@Profile("dev")
public class UserSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void seedUsers() {
        List<User> users = List.of(
                new User("admin", "admin", "admin", "admin@gmail.com", passwordEncoder.encode("admin"), RoleEnum.ADMIN),
                new User("user", "user", "user", "user@gmail.com", passwordEncoder.encode("user"), RoleEnum.USER)
        );
        this.userRepository.saveAll(users);
    }

    @Override
    public void run(String... args) throws Exception {
        seedUsers();
    }
}
