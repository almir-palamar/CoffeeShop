package com.example.coffeeshop.controllers.v1;

import com.example.coffeeshop.dto.JwtTokenDTO;
import com.example.coffeeshop.models.User;
import com.example.coffeeshop.requests.LogInRequest;
import com.example.coffeeshop.requests.RegisterRequest;
import com.example.coffeeshop.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public JwtTokenDTO login(@RequestBody LogInRequest logInRequest) {
        return this.authService.login(logInRequest);
    }

    @PostMapping("/register")
    public JwtTokenDTO register(@RequestBody @Valid RegisterRequest registerRequest) {
        return this.authService.register(registerRequest);
    }

    @GetMapping("/logout")
    public void logout(@RequestHeader("Authorization") String jwtToken) {
        this.authService.logout(jwtToken);
    }

    @GetMapping("/me")
    public User me() {
        return this.authService.me();
    }

}
