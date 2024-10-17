package com.example.coffeeshop.dto.auth;

import jakarta.validation.constraints.NotEmpty;

public record RegisterDTO(
    @NotEmpty String firstName,
    @NotEmpty String lastName,
    @NotEmpty String username,
    @NotEmpty String email,
    @NotEmpty String password
) { }
