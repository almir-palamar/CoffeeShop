package com.example.coffeeshop.dto.order;

import com.example.coffeeshop.dto.coffee.CoffeeDTO;

public record OrderItemDTO(
        CoffeeDTO coffee,
        Integer quantity
) {
}
