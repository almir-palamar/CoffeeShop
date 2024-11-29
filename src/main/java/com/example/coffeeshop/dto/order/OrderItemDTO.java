package com.example.coffeeshop.dto.order;

import com.example.coffeeshop.models.Coffee;

public record OrderItemDTO(
        Coffee coffee,
        Float total,
        Integer quantity
) {
}
