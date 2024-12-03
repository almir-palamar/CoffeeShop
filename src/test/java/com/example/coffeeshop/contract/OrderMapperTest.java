package com.example.coffeeshop.contract;

import com.example.coffeeshop.dto.coffee.CoffeeDTO;
import com.example.coffeeshop.dto.order.OrderDTO;
import com.example.coffeeshop.enums.OrderEnum;
import com.example.coffeeshop.mappers.CoffeeMapper;
import com.example.coffeeshop.mappers.OrderMapper;
import com.example.coffeeshop.models.Coffee;
import com.example.coffeeshop.models.Order;
import com.example.coffeeshop.models.OrderItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class OrderMapperTest {

    @InjectMocks
    private OrderMapper orderMapper;

    @Test
    void shouldReturnOrderDTO() {
        List<OrderItem> orderItems = List.of(
                OrderItem.builder()
                        .coffee(
                                Coffee.builder()
                                        .id(1L)
                                        .name("Espresso")
                                        .caffeineGram(8)
                                        .brewTime(40)
                                        .price(1.60f)
                                        .build()
                        )
                        .quantity(1).build()
        );

        String orderNumber = UUID.randomUUID().toString();
        Order order = Order.builder()
                .id(1L)
                .type(OrderEnum.Type.TO_GO)
                .status(OrderEnum.Status.PENDING)
                .orderNumber(orderNumber)
                .orderItems(orderItems)
                .build();

        OrderDTO orderDTO = orderMapper.toOrderDTO(order);

        assertThat(orderDTO.type()).isEqualTo(order.getType());
        assertThat(orderDTO.orderItems().size()).isEqualTo(order.getOrderItems().size());
        assertThat(orderDTO.orderNumber()).isEqualTo(order.getOrderNumber());
        assertThat(orderDTO.orderItems().getFirst().quantity()).isEqualTo(order.getOrderItems().getFirst().getQuantity());
        assertThat(orderDTO.orderItems().getFirst().name()).isEqualTo(order.getOrderItems().getFirst().getCoffee().getName());
    }
}