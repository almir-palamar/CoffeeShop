package com.example.coffeeshop.services;

import com.example.coffeeshop.models.Barista;
import com.example.coffeeshop.models.Order;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

@Service
@AllArgsConstructor
public class ProcessOrderService extends Thread {

    private final Queue<Order> ordersToProcess = new ConcurrentLinkedDeque<>();
    private final BaristaService baristaService;
    private final EspressoMachineService espressoMachineService;
    private final OrderService orderService;

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
                            ProcessOrder po = new ProcessOrder(
                                    baristaService,
                                    espressoMachineService,
                                    orderService,
                                    orderToProcess,
                                    baristaToTakeOrder
                            );
                            po.start();
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

}
