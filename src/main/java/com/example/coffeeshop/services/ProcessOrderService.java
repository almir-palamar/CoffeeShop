package com.example.coffeeshop.services;

import com.example.coffeeshop.enums.BaristaStatusEnum;
import com.example.coffeeshop.enums.OrderEnum;
import com.example.coffeeshop.models.Barista;
import com.example.coffeeshop.models.Coffee;
import com.example.coffeeshop.models.Order;
import com.example.coffeeshop.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Component
public class ProcessOrderService {

    private final Queue<Order> orders = new LinkedList<>();
    private final OrderRepository orderRepository;
    private final BaristaService baristaService;
    private final EspressoMachineService espressoMachineService;

    public ProcessOrderService(OrderRepository orderRepository,
                               BaristaService baristaService,
                               EspressoMachineService espressoMachineService) {
        this.orderRepository = orderRepository;
        this.baristaService = baristaService;
        this.espressoMachineService = espressoMachineService;
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }

    @Transactional
    @Async("asyncExecutor")
    public void processOrder(@NonNull Order order) {
        try {
            addOrder(order);
            while (!this.orders.isEmpty()) {

                List<Barista> availableBaristas = baristaService.findAvailableBaristas();
                if (!availableBaristas.isEmpty()) {
                    Barista barista = availableBaristas.getFirst();
                    Order orderToProcess = this.orders.poll();
                    barista.setStatus(BaristaStatusEnum.MAKING_COFFEE);

                    int processingTime = orderToProcess.getCoffees().stream()
                            .mapToInt(Coffee::getBrewTime)
                            .sum();

                    int coffeeAmount = orderToProcess.getCoffees().stream()
                            .mapToInt(Coffee::getCaffeineGram)
                            .sum();

                    orderToProcess.setBarista(barista);

                    Thread.sleep(processingTime * 1000L);

                    orderToProcess.setStatus(OrderEnum.Status.DELIVERED);
                    this.espressoMachineService.updateGrinderCoffeeAmount(coffeeAmount, barista.getEspressoMachine());
                    barista.setStatus(BaristaStatusEnum.AVAILABLE);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
