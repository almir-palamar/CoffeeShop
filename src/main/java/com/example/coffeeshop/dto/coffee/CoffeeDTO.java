package com.example.coffeeshop.dto.coffee;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CoffeeDTO(
        @NotEmpty String name,
        @NotNull Integer brewTime,
        @NotNull Integer caffeineGram,
        @NotNull Float price
) {}
