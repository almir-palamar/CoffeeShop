package com.example.coffeeshop.app.order;

import com.example.coffeeshop.enums.BaristaStatusEnum;
import com.example.coffeeshop.enums.OrderEnum;
import com.example.coffeeshop.models.Barista;
import com.example.coffeeshop.models.Coffee;
import com.example.coffeeshop.models.Order;
import com.example.coffeeshop.services.BaristaService;
import com.example.coffeeshop.services.EspressoMachineService;
import com.example.coffeeshop.services.OrderService;

public class OrderThread extends Thread {

    private Order order;
    private final OrderService orderService;
    private final BaristaService baristaService;
    private final EspressoMachineService espressoMachineService;

    public OrderThread(Order order,
                       OrderService orderService,
                       BaristaService baristaService,
                       EspressoMachineService espressoMachineService) {
        this.order = order;
        this.orderService = orderService;
        this.baristaService = baristaService;
        this.espressoMachineService = espressoMachineService;
    }

    public void prepareOrder() {
        try {
            Barista barista = baristaService.findBaristasByName(order.getBarista().getName());
            baristaService.updateBaristaStatus(barista.getName(), BaristaStatusEnum.MAKING_COFFEE);

            int processingTime = order.getCoffees().stream()
                    .mapToInt(Coffee::getBrewTime)
                    .sum();

            int coffeeAmount = order.getCoffees().stream()
                    .mapToInt(Coffee::getCaffeineGram)
                    .sum();

            sleep(processingTime * 1000L);

            orderService.updateOrderStatus(order.getId(), OrderEnum.Status.DELIVERED);
            baristaService.updateBaristaStatus(barista.getName(), BaristaStatusEnum.AVAILABLE);
            espressoMachineService.updateGrinderCoffeeAmount(coffeeAmount, barista.getEspressoMachine());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.interrupt();
    }

    @Override
    public void run() {
        prepareOrder();
    }

}
