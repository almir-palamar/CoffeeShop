package com.example.coffeeshop.services;

import com.example.coffeeshop.models.Coffee;
import com.example.coffeeshop.models.Order;
import com.example.coffeeshop.repositories.CoffeeRepository;
import com.example.coffeeshop.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final CoffeeRepository coffeeRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, CoffeeRepository coffeeRepository) {
        this.orderRepository = orderRepository;
        this.coffeeRepository = coffeeRepository;
    }

    @Transactional
    public Order save(Order order) {
        List<Coffee> coffees = new ArrayList<>();
        order.getCoffees().forEach(item -> {
            Optional<Coffee> coffee = this.coffeeRepository.findByName(item.getName());
            if (coffee.isPresent()) {
                coffees.add(coffee.get());
            } else {
                try {
                    throw new Exception("Coffee not found");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        order.setCoffees(coffees);
        return this.orderRepository.save(order);
    }

    public Order findById(Long Id) {
        Optional<Order> order = this.orderRepository.findById(Id);
        return order.orElse(null);
    }

}
