package com.example.coffeeshop.services;

import com.example.coffeeshop.dto.jwt.JwtTokenDTO;
import com.example.coffeeshop.exceptions.UnauthorizedException;
import com.example.coffeeshop.requests.LogInRequest;
import com.example.coffeeshop.requests.RegisterRequest;
import com.example.coffeeshop.models.User;
import com.example.coffeeshop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    @Autowired
    public AuthService(UserRepository userRepository,
                       UserService userService,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager,
                       EmailService emailService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.emailService = emailService;
    }

    public JwtTokenDTO register(RegisterRequest registerRequest) {
        User user = new User(
                registerRequest.getFirstName(),
                registerRequest.getLastName(),
                registerRequest.getUsername(),
                registerRequest.getEmail(),
                passwordEncoder.encode(registerRequest.getPassword()),
                null
        );
        userService.save(user);
        this.emailService.sendWelcomeEmail("user@gmail.com");
        return jwtService.generateToken(user);
    }

    public JwtTokenDTO login(LogInRequest logInRequest) {
        Optional<User> user = userRepository.findByUsername(logInRequest.getUsername());
        if (user.isEmpty()) {
            throw new UnauthorizedException("Username not valid.");
        }
        if (!passwordEncoder.matches(logInRequest.getPassword(), user.get().getPassword())) {
            throw new UnauthorizedException("Password not valid.");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(logInRequest.getUsername(), logInRequest.getPassword()));
        return jwtService.generateToken(user.get());
    }

    public void logout(String jwtToken) {
        jwtService.invalidateToken(jwtToken);
    }

    public User me() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
