package com.example.coffeeshop.services;

import com.example.coffeeshop.app.order.OrderThread;
import com.example.coffeeshop.enums.BaristaStatusEnum;
import com.example.coffeeshop.enums.OrderEnum;
import com.example.coffeeshop.models.Barista;
import com.example.coffeeshop.models.Coffee;
import com.example.coffeeshop.models.Order;
import com.example.coffeeshop.repositories.BaristaRepository;
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
    private final BaristaService baristaService;
    private final BaristaRepository baristaRepository;
    private final EspressoMachineService espressoMachineService;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        CoffeeRepository coffeeRepository,
                        BaristaService baristaService,
                        EspressoMachineService espressoMachineService, BaristaRepository baristaRepository) {
        this.orderRepository = orderRepository;
        this.coffeeRepository = coffeeRepository;
        this.baristaService = baristaService;
        this.espressoMachineService = espressoMachineService;
        this.baristaRepository = baristaRepository;
    }

    @Transactional
    public Order save(@NonNull Order order) {
        Barista barista = baristaRepository.findBaristasByStatus(BaristaStatusEnum.AVAILABLE).getFirst();
        List<Coffee> coffees = new ArrayList<>();
        order.getCoffees().forEach(item -> {
            Optional<Coffee> coffee = this.coffeeRepository.findByName(item.getName());
            coffees.add(coffee.get());
        });
        order.setBarista(barista);
        order.setCoffees(coffees);
        if (barista != null) {
            OrderThread orderThread = new OrderThread(
                    order, this, baristaService, espressoMachineService);
            orderThread.start();
        }
        return this.orderRepository.save(order);
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

}
