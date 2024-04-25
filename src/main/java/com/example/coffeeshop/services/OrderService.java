package com.example.coffeeshop.services;

import com.example.coffeeshop.enums.OrderEnum;
import com.example.coffeeshop.models.Coffee;
import com.example.coffeeshop.models.Order;
import com.example.coffeeshop.repositories.CoffeeRepository;
import com.example.coffeeshop.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final CoffeeRepository coffeeRepository;
    private final OrderRepository orderRepository;
    private final ProcessOrderService processOrderService;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        CoffeeRepository coffeeRepository,
                        ProcessOrderService processOrderService) {
        this.orderRepository = orderRepository;
        this.coffeeRepository = coffeeRepository;
        this.processOrderService = processOrderService;
    }

    public Order findById(Long Id) {
        Optional<Order> order = this.orderRepository.findById(Id);
        return order.orElse(null);
    }

    public Order updateOrderStatus(Long orderId, OrderEnum.Status status) {
        Order order = this.findById(orderId);
        if (order != null) {
            order.setStatus(status);
            orderRepository.save(order);
        }
        return order;
    }

    @Transactional
    public Order save(@NonNull Order order) {
        List<Coffee> coffees = new ArrayList<>();
        order.getCoffees().forEach(item -> {
            Optional<Coffee> coffee = this.coffeeRepository.findByName(item.getName());
            coffees.add(coffee.get());
        });
        order.setCoffees(coffees);
        order.setType(order.getType());
        order.setStatus(order.getStatus());

        processOrderService.processOrder(order);
        return this.orderRepository.save(order);
    }

}
