package com.example.coffeeshop.dto.order;

import com.example.coffeeshop.dto.coffee.CoffeeOrderItemDTO;
import com.example.coffeeshop.enums.OrderEnum;

import java.util.List;

public record OrderDTO(
    List<CoffeeOrderItemDTO> orderItems,
    String orderNumber,
    Float orderTotal,
    OrderEnum.Type type,
    OrderEnum.Status status
) { }