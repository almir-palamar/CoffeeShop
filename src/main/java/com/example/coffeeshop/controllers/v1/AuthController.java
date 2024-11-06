package com.example.coffeeshop.controllers.v1;

import com.example.coffeeshop.dto.auth.JwtTokenDTO;
import com.example.coffeeshop.dto.auth.LoginDTO;
import com.example.coffeeshop.dto.auth.RegisterDTO;
import com.example.coffeeshop.models.User;
import com.example.coffeeshop.services.AuthService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDTO> login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(authService.login(loginDTO));
    }

    @PostMapping("/register")
    public JwtTokenDTO register(@RequestBody @Valid RegisterDTO registerDTO) {
        return this.authService.register(registerDTO);
    }

    @GetMapping("/logout")
    @SecurityRequirement(name = "JWTAuth")
    public void logout(@RequestHeader("Authorization") String jwtToken) {
        this.authService.logout(jwtToken);
    }

    @GetMapping("/me")
    @SecurityRequirement(name = "JWTAuth")
    public User me() {
        return this.authService.me();
    }

}
