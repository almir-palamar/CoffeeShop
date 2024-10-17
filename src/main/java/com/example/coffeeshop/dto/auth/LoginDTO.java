package com.example.coffeeshop.dto.auth;

import jakarta.validation.constraints.NotEmpty;

public record LoginDTO(
   @NotEmpty String username,
   @NotEmpty String password
) {}
