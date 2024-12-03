package com.example.coffeeshop.controllers.v1;

import com.example.coffeeshop.dto.order.CreateOrderRequest;
import com.example.coffeeshop.dto.order.OrderDTO;
import com.example.coffeeshop.services.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@Tag(name = "Orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // Requirement is to have separate routes for different order types
    @PostMapping("/to-go")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO orderToGo(@Valid @RequestBody CreateOrderRequest orderRequest) {
        return orderService.save(orderRequest);
    }

    @PostMapping("/web-ui")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO orderWebUI(@Valid @RequestBody CreateOrderRequest orderRequest) {
        return orderService.save(orderRequest);
    }

    @GetMapping("/{id}")
    public OrderDTO getOrder(@PathVariable Long id) {
        return orderService.findById(id);
    }

}
