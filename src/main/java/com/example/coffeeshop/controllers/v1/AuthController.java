package com.example.coffeeshop.controllers.v1;

import com.example.coffeeshop.dto.auth.JwtTokenDTO;
import com.example.coffeeshop.dto.auth.LoginDTO;
import com.example.coffeeshop.dto.auth.RegisterDTO;
import com.example.coffeeshop.models.User;
import com.example.coffeeshop.services.AuthService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final LogoutHandler logoutHandler;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public JwtTokenDTO login(@RequestBody LoginDTO loginDTO) {
        return authService.login(loginDTO);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public JwtTokenDTO register(@RequestBody @Valid RegisterDTO registerDTO) {
        return authService.register(registerDTO);
    }

    @GetMapping("/logout")
    @SecurityRequirement(name = "JWTAuth")
    public String logout(@RequestHeader("Authorization") String jwtToken,
                         HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        logoutHandler.logout(request, response, authentication);
        return "Successfully logged out!";
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = "JWTAuth")
    public User me() {
        return authService.me();
    }

}
