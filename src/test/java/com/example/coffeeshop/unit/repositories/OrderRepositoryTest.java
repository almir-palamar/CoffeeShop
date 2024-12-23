package com.example.coffeeshop.unit.repositories;

import com.example.coffeeshop.enums.OrderEnum;
import com.example.coffeeshop.models.Order;
import com.example.coffeeshop.repositories.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class OrderRepositoryTest extends RepositoryBaseTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void shouldCountOrderByStatusIsAndTypeAndReturn() {
        Order order = Order.builder()
                .id(1L)
                .type(OrderEnum.Type.TO_GO)
                .status(OrderEnum.Status.PENDING)
                .build();
        orderRepository.save(order);

        Integer foundOrders = orderRepository.countOrderByStatusIsAndType(order.getStatus(), order.getType());

        assertThat(foundOrders).isEqualTo(1);
    }
}