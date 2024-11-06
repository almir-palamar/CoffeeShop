package com.example.coffeeshop.controllers.v1;

import com.example.coffeeshop.dto.order.OrderDTO;
import com.example.coffeeshop.enums.OrderEnum;
import com.example.coffeeshop.models.Order;
import com.example.coffeeshop.services.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@Tag(name = "Orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Requirement is to have separate routes for different order types
    @PostMapping("/to-go")
    public Order orderToGo(@Valid @RequestBody OrderDTO orderDTO) {
        return this.orderService.save(new OrderDTO(orderDTO.coffees(), OrderEnum.Type.TO_GO));
    }

    @PostMapping("/web-ui")
    public ResponseEntity<Order> orderWebUI(@Valid @RequestBody OrderDTO orderDTO) {
        Order order = this.orderService.save(new OrderDTO(orderDTO.coffees(), OrderEnum.Type.WEB_UI));
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        return this.orderService.findById(id);
    }

}
