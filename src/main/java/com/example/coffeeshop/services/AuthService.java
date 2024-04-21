package com.example.coffeeshop.services;

import com.example.coffeeshop.dto.LogInRequest;
import com.example.coffeeshop.dto.RegisterRequest;
import com.example.coffeeshop.models.User;
import com.example.coffeeshop.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository,
                       UserService userService,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public String register(RegisterRequest registerRequest) {
        User user = new User(
                registerRequest.getName(),
                registerRequest.getUsername(),
                passwordEncoder.encode(registerRequest.getPassword())
        );
        userService.save(user);
        return jwtService.generateToken(user);
    }

    public String login(LogInRequest logInRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(logInRequest.getUsername(), logInRequest.getPassword())
        );
        User user = userRepository.findByUsername(logInRequest.getUsername());
        if (user == null) {
            throw new IllegalArgumentException();
        }
        return jwtService.generateToken(user);
    }
}
