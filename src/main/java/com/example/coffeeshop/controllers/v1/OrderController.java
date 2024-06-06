package com.example.coffeeshop.controllers.v1;

import com.example.coffeeshop.enums.OrderEnum;
import com.example.coffeeshop.models.Order;
import com.example.coffeeshop.requests.CreateOrderRequest;
import com.example.coffeeshop.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/to-go")
    public Order orderToGo(@Valid @RequestBody CreateOrderRequest orderRequest) {
        orderRequest.setType(OrderEnum.Type.TO_GO);
        return this.orderService.save(orderRequest);
    }

    @PostMapping("/web-ui")
    public ResponseEntity<Order> orderWebUI(@Valid @RequestBody CreateOrderRequest orderRequest) {
        Order order = this.orderService.save(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        return this.orderService.findById(id);
    }

}
