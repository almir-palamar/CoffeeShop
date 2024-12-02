package com.example.coffeeshop.services;

import com.example.coffeeshop.dto.order.CreateOrderRequest;
import com.example.coffeeshop.dto.order.OrderDTO;
import com.example.coffeeshop.enums.OrderEnum;
import com.example.coffeeshop.exceptions.EntityNotFoundException;
import com.example.coffeeshop.mappers.OrderMapper;
import com.example.coffeeshop.models.Order;
import com.example.coffeeshop.models.OrderItem;
import com.example.coffeeshop.repositories.CoffeeRepository;
import com.example.coffeeshop.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

    private final CoffeeRepository coffeeRepository;
    private final OrderRepository orderRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final OrderMapper orderMapper;

    public OrderDTO findById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return orderMapper.toOrderDTO(order);
    }

    public OrderDTO updateOrderStatus(Long orderId, OrderEnum.Status status) {
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
        order.setStatus(status);
        orderRepository.save(order);
        return orderMapper.toOrderDTO(order);
    }

    @Transactional
    public OrderDTO save(@NonNull CreateOrderRequest createOrder) {
        List<OrderItem> orderItems = new ArrayList<>();

        createOrder.orderItemDTOS().forEach(orderItemDTO -> {
            orderItems.add(new OrderItem(
                    coffeeRepository.findByName(orderItemDTO.coffeeDTO().name()).orElseThrow(EntityNotFoundException::new),
                    orderItemDTO.quantity()));
        });

        Order newOrder = new Order(orderItems, createOrder.type());
        eventPublisher.publishEvent(newOrder);
        return orderMapper.toOrderDTO(orderRepository.save(newOrder));
    }

    public Integer countOrderByStatusIsAndType(OrderEnum.Status status, OrderEnum.Type type) {
        return orderRepository.countOrderByStatusIsAndType(status, type);
    }

}
