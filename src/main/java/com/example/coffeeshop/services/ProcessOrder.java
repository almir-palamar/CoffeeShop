package com.example.coffeeshop.services;

import com.example.coffeeshop.enums.BaristaStatusEnum;
import com.example.coffeeshop.enums.OrderEnum;
import com.example.coffeeshop.models.Barista;
import com.example.coffeeshop.models.Coffee;
import com.example.coffeeshop.models.Order;
import com.example.coffeeshop.models.OrderItem;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProcessOrder extends Thread{

    private final BaristaService baristaService;
    private final EspressoMachineService espressoMachineService;
    private final OrderService orderService;
    private final Order order;
    private final Barista baristaToTakeOrder;

    @Override
    public void run() {
        try {
            processOrder(baristaToTakeOrder, order);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void processOrder(Barista baristaToTakeOrder, Order order) throws InterruptedException {
        if (!baristaService.isThereEnoughCaffeineInGrinder(baristaToTakeOrder, this.calculateCaffeineNeeded())) {
            baristaService.updateBaristaStatus(baristaToTakeOrder.getId(), BaristaStatusEnum.FILLING_GRINDER);
            espressoMachineService.refillGrinder(baristaToTakeOrder.getEspressoMachine().getId());
            sleep(20000); // put here global variable for the time needed to refill grinder
        }
        baristaService.updateBaristaStatus(baristaToTakeOrder.getId(), BaristaStatusEnum.MAKING_COFFEE);
        sleep(this.calculateProcessingTime() * 1000);
        baristaService.updateBaristaStatus(baristaToTakeOrder.getId(), BaristaStatusEnum.AVAILABLE);
        orderService.updateOrderStatus(order.getId(), OrderEnum.Status.DELIVERED);
        espressoMachineService.updateGrinderCoffeeAmount(
                this.calculateCaffeineNeeded(), baristaToTakeOrder.getEspressoMachine());
    }

    private Integer calculateProcessingTime() {
        return order.getOrderItems().stream().mapToInt(
                order -> order.getCoffee().getBrewTime() * order.getQuantity()
        ).sum();
    }

    private Integer calculateCaffeineNeeded() {
        return order.getOrderItems().stream().mapToInt(
                order -> order.getCoffee().getCaffeineGram() * order.getQuantity()
        ).sum();
    }
}
