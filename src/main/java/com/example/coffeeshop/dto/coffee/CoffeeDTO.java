package com.example.coffeeshop.dto.coffee;

public record CoffeeDTO(
        String name,
        Integer brewTime,
        Integer caffeineGram,
        Float price,
        String imagePath
) {}
