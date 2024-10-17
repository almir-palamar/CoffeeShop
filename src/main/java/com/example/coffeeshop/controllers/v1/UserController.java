package com.example.coffeeshop.controllers.v1;

import com.example.coffeeshop.dto.user.UserDTO;
import com.example.coffeeshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDTO> getUsers() {
        return userService.findAll();
    }

    @GetMapping("/me")
    public Authentication me() {
        return this.userService.me();
    }

}
