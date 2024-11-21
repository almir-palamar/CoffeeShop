package com.example.coffeeshop.services;

import com.example.coffeeshop.dto.order.OrderDTO;
import com.example.coffeeshop.enums.OrderEnum;
import com.example.coffeeshop.models.Coffee;
import com.example.coffeeshop.models.Order;
import com.example.coffeeshop.repositories.CoffeeRepository;
import com.example.coffeeshop.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderService {

    private final CoffeeRepository coffeeRepository;
    private final OrderRepository orderRepository;
    private final ApplicationEventPublisher eventPublisher;

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
    public Order save(@NonNull OrderDTO orderDTO) {
        List<Coffee> coffees = new ArrayList<>();
        orderDTO.coffees().forEach(item -> {
            Optional<Coffee> coffee = this.coffeeRepository.findByName(item);
            coffees.add(coffee.get());
        });
        Order order = new Order(coffees, orderDTO.type());
        eventPublisher.publishEvent(order);
        return this.orderRepository.save(order);
    }

    public Integer countOrderByStatusIsAndType(OrderEnum.Status status, OrderEnum.Type type) {
        return this.orderRepository.countOrderByStatusIsAndType(status, type);
    }

}
