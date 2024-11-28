package com.example.coffeeshop.mappers;

import com.example.coffeeshop.dto.order.OrderDTO;
import com.example.coffeeshop.models.Coffee;
import com.example.coffeeshop.models.Order;

public class OrderMapper {

    public OrderDTO toOrderDTO(Order order) {
        return new OrderDTO(
                order.getCoffees().stream().map(Coffee::getName).toList(),
                order.getType()
        );
    }
}
