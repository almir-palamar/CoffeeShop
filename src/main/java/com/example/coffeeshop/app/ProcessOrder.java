package com.example.coffeeshop.app;

import com.example.coffeeshop.enums.OrderEnum;
import com.example.coffeeshop.models.Coffee;
import com.example.coffeeshop.models.Order;
import com.example.coffeeshop.services.OrderService;

public class ProcessOrder {

    private final OrderService orderService;

    public ProcessOrder(OrderService orderService) {
        this.orderService = orderService;
    }

    public Order processOrder(Order order) throws InterruptedException {
        int processingTime = order.getCoffees().stream()
                .mapToInt(Coffee::getBrewTime)
                .sum();

        Thread.sleep(processingTime * 1000L);
        return orderService.updateOrderStatus(order.getId(), OrderEnum.Status.DELIVERED);
    }

}
