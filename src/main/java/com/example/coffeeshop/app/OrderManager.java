package com.example.coffeeshop.app;

import com.example.coffeeshop.enums.BaristaStatusEnum;
import com.example.coffeeshop.enums.OrderEnum;
import com.example.coffeeshop.models.Barista;
import com.example.coffeeshop.models.Coffee;
import com.example.coffeeshop.models.Order;
import com.example.coffeeshop.services.BaristaService;
import com.example.coffeeshop.services.OrderService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Queue;

@Component
public class OrderManager {

//    private final OrderService orderService;
//    private final BaristaService baristaService;
//    private Queue<Order> orders;

//    public OrderManager(OrderService orderService, BaristaService baristaService) {
//        this.orderService = orderService;
//        this.baristaService = baristaService;
//    }
//
//    public void addOrder(Order order) {
//        this.orders.add(order);
//        //TODO: here emmit an event that there is an order receieved
//        // create OrderReceivedEvent
//
//    }

//    public void processOrder() throws InterruptedException {
//        Order order = orders.poll();
//        List<Barista> availableBaristas = baristaService.findAvailableBaristas();
//        if (availableBaristas.isEmpty()) {
//            System.out.println("There is no barista available");
//            return;
//        }
//        assert order != null;
//        order.setBarista(availableBaristas.getFirst());
//        while (order != null) {
//            this.simulatePreparation(order);
//            order = orders.poll();
//        }
//    }

//    public void simulatePreparation(Order order) throws InterruptedException {
//        int processingTime = order.getCoffees().stream()
//                .mapToInt(Coffee::getBrewTime)
//                .sum();
//
//        Thread.sleep(processingTime * 1000L);
//        orderService.updateOrderStatus(order.getId(), OrderEnum.Status.DELIVERED); // move order to status delivered
//        baristaService.updateBaristaStatus(order.getBarista().getName(), BaristaStatusEnum.AVAILABLE); // set barista status to available
//
//        //TODO dispatch finished OrderReadyEvent
//    }
}
