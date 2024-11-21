package com.example.coffeeshop.services;

import com.example.coffeeshop.dto.auth.JwtTokenDTO;
import com.example.coffeeshop.dto.auth.LoginDTO;
import com.example.coffeeshop.dto.auth.RegisterDTO;
import com.example.coffeeshop.exceptions.UnauthorizedException;
import com.example.coffeeshop.models.User;
import com.example.coffeeshop.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    public JwtTokenDTO register(RegisterDTO registerDTO) {
        User user = new User(
                registerDTO.firstName(),
                registerDTO.lastName(),
                registerDTO.username(),
                registerDTO.email(),
                passwordEncoder.encode(registerDTO.password()),
                null
        );
        userService.save(user);
        this.emailService.sendWelcomeEmail(registerDTO.email());
        return jwtService.generateToken(user);
    }

    public JwtTokenDTO login(LoginDTO loginDTO) {
        Optional<User> user = userRepository.findByUsername(loginDTO.username());
        if (user.isEmpty()) {
            throw new UnauthorizedException("Username not valid.");
        }
        if (!passwordEncoder.matches(loginDTO.password(), user.get().getPassword())) {
            throw new UnauthorizedException("Password not valid.");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.username(), loginDTO.password()));
        return jwtService.generateToken(user.get());
    }

    public void logout(String jwtToken) {
        jwtService.invalidateToken(jwtToken);
        SecurityContextHolder.clearContext();
    }

    public User me() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
