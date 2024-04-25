package com.example.coffeeshop.controllers;

import com.example.coffeeshop.enums.OrderEnum;
import com.example.coffeeshop.models.Order;
import com.example.coffeeshop.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // check this route with middleware
    @PostMapping("/to-go")
    public Order orderToGo(@RequestBody Order order) {
        order.setType(OrderEnum.Type.TO_GO);
        return this.orderService.save(order);
    }

    @PostMapping("/web-ui")
    public Order orderWebUI(@RequestBody Order order) {
        return this.orderService.save(order);
    }

    @GetMapping("/{Id}")
    public Order getOrder(@PathVariable Long Id) {
        return this.orderService.findById(Id);
    }

}
