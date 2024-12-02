package com.example.coffeeshop.mappers;

import com.example.coffeeshop.dto.order.OrderDTO;
import com.example.coffeeshop.models.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderDTO toOrderDTO(Order order) {
        return new OrderDTO(
                order.getOrderItems().stream().map(
                        orderItem -> orderItem.getCoffee().getName() + " x" + orderItem.getQuantity()
                ).toList(),
                order.getOrderNumber(),
                order.getType(),
                order.getStatus()
        );
    }
}
