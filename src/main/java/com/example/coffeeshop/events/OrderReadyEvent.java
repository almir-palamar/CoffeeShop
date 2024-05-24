package com.example.coffeeshop.events;

import com.example.coffeeshop.models.Order;

public class OrderReadyEvent {

    private Order order;

    public OrderReadyEvent(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

}
