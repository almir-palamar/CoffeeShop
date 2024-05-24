package com.example.coffeeshop.services;

import com.example.coffeeshop.app.OrderManager;
import com.example.coffeeshop.enums.OrderEnum;
import com.example.coffeeshop.models.Coffee;
import com.example.coffeeshop.models.Order;
import com.example.coffeeshop.repositories.CoffeeRepository;
import com.example.coffeeshop.repositories.OrderRepository;
import com.example.coffeeshop.requests.CreateOrderRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final CoffeeRepository coffeeRepository;
    private final OrderRepository orderRepository;
    private final OrderManager orderManager;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        CoffeeRepository coffeeRepository, OrderManager orderManager) {
        this.orderRepository = orderRepository;
        this.coffeeRepository = coffeeRepository;
        this.orderManager = orderManager;
    }

    public Order findById(Long id) {
        Optional<Order> order = this.orderRepository.findById(id);
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
    public Order save(@NonNull CreateOrderRequest orderRequest) {
        List<Coffee> coffees = new ArrayList<>();
        orderRequest.getCoffees().forEach(item -> {
            Optional<Coffee> coffee = this.coffeeRepository.findByName(item);
            coffees.add(coffee.get());
        });
        Order order = new Order(coffees);
        order.setType(orderRequest.getType());

        orderManager.addOrder(order);

        return this.orderRepository.save(order);
    }

    public Integer countOrderByStatusIsAndType(OrderEnum.Status status, OrderEnum.Type type) {
        return this.orderRepository.countOrderByStatusIsAndType(status, type);
    }

}
