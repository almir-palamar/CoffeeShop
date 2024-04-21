package com.example.coffeeshop.controllers;

import com.example.coffeeshop.dto.LogInRequest;
import com.example.coffeeshop.dto.RegisterRequest;
import com.example.coffeeshop.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public String login(@RequestBody LogInRequest logInRequest) {
        return this.authService.login(logInRequest);
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest registerRequest) {
        return this.authService.register(registerRequest);
    }

}
