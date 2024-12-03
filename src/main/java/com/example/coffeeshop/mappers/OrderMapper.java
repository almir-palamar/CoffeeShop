package com.example.coffeeshop.mappers;

import com.example.coffeeshop.dto.coffee.CoffeeOrderItemDTO;
import com.example.coffeeshop.dto.order.OrderDTO;
import com.example.coffeeshop.models.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderDTO toOrderDTO(Order order) {
        return new OrderDTO(
            order.getOrderItems().stream().map(
                orderItem -> new CoffeeOrderItemDTO(
                       orderItem.getCoffee().getName(),
                       orderItem.getCoffee().getBrewTime(),
                       orderItem.getCoffee().getCaffeineGram(),
                       orderItem.getCoffee().getPrice(),
                       orderItem.getQuantity()
               )
            ).toList(),
            order.getOrderNumber(),
            order.getTotal(),
            order.getType(),
            order.getStatus()
        );
    }
}
