package com.example.coffeeshop.dto.order;

import com.example.coffeeshop.enums.OrderEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CreateOrderRequest(
        @NotEmpty(message = "pick_at_least_one_coffee")
        List<OrderItemDTO> orderItems,
        @Enumerated(value = EnumType.STRING)
        OrderEnum.Type type
) {
}
