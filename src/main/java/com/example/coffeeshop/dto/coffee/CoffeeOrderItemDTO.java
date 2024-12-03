package com.example.coffeeshop.dto.coffee;

public record CoffeeOrderItemDTO(
        String name,
        Integer brewTime,
        Integer caffeineGram,
        Float price,
        Integer quantity
) {
}
