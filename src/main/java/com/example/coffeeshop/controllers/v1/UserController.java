package com.example.coffeeshop.controllers.v1;

import com.example.coffeeshop.dto.user.UserDTO;
import com.example.coffeeshop.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @SecurityRequirement(name = "JWTAuth")
    public List<UserDTO> getUsers() {
        return userService.findAll();
    }

}
