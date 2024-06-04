package com.example.coffeeshop.dto;

import java.util.List;

public record UserDTO(
    String fullName,
    String username,
    String email,
    List<String> authorities
) {}
