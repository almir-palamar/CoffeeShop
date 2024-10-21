package com.example.coffeeshop.services;

import com.example.coffeeshop.enums.BaristaStatusEnum;
import com.example.coffeeshop.enums.OrderEnum;
import com.example.coffeeshop.models.Barista;
import com.example.coffeeshop.models.Coffee;
import com.example.coffeeshop.models.Order;
import jakarta.transaction.Transactional;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

@Service
public class ProcessOrderService extends Thread {

    private final Queue<Order> ordersToProcess = new ConcurrentLinkedDeque<>();
    private final BaristaService baristaService;
    private final EspressoMachineService espressoMachineService;
    private final OrderService orderService;

    public ProcessOrderService(
            BaristaService baristaService,
            EspressoMachineService espressoMachineService,
            OrderService orderService
    ) {
        this.baristaService = baristaService;
        this.espressoMachineService = espressoMachineService;
        this.orderService = orderService;
        this.start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                Order orderToProcess = ordersToProcess.peek();
                if (orderToProcess != null) {
                    List<Barista> availableBaristas = this.baristaService.findAvailableBaristas();
                    if (!availableBaristas.isEmpty()) {
                        Barista baristaToTakeOrder = availableBaristas.getFirst();
                        if (baristaToTakeOrder != null) {
                            ordersToProcess.remove();
                            processOrder(baristaToTakeOrder, orderToProcess);
                        }
                    } else {
                        sleep(10000);
                    }
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @EventListener
    public void handleNewOrderReceived(Order order) {
        this.ordersToProcess.add(order);
    }

    @Async
    @Transactional
    public void processOrder(Barista baristaToTakeOrder, Order order) throws InterruptedException {
        if (!baristaService.isThereEnoughCaffeineInGrinder(baristaToTakeOrder, this.calculateCaffeineNeeded(order))) {
            baristaService.updateBaristaStatus(baristaToTakeOrder.getId(), BaristaStatusEnum.FILLING_GRINDER);
            espressoMachineService.refillGrinder(baristaToTakeOrder.getEspressoMachine().getId());
            sleep(20000); // put here global variable for the time needed to refill grinder
        }
        baristaService.updateBaristaStatus(baristaToTakeOrder.getId(), BaristaStatusEnum.MAKING_COFFEE);
        sleep(this.calculateProcessingTime(order) * 1000);
        baristaService.updateBaristaStatus(baristaToTakeOrder.getId(), BaristaStatusEnum.AVAILABLE);
        orderService.updateOrderStatus(order.getId(), OrderEnum.Status.DELIVERED);
        espressoMachineService.updateGrinderCoffeeAmount(
                this.calculateCaffeineNeeded(order), baristaToTakeOrder.getEspressoMachine());
    }

    private Integer calculateProcessingTime(Order order) {
        return order.getCoffees().stream().mapToInt(Coffee::getBrewTime).sum();
    }

    private Integer calculateCaffeineNeeded(Order order) {
        return order.getCoffees().stream().mapToInt(Coffee::getCaffeineGram).sum();
    }
}
