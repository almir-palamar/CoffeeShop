package com.example.coffeeshop.unit.services;

import com.example.coffeeshop.dto.order.OrderDTO;
import com.example.coffeeshop.enums.OrderEnum;
import com.example.coffeeshop.mappers.OrderMapper;
import com.example.coffeeshop.models.Coffee;
import com.example.coffeeshop.models.Order;
import com.example.coffeeshop.repositories.OrderRepository;
import com.example.coffeeshop.services.OrderService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @Test
    void shouldFindOrderById() {
        List<Coffee> orderCoffees = List.of(
            Coffee.builder()
                .id(1L)
                .name("Espresso")
                .caffeineGram(8)
                .brewTime(40)
                .price(1.60f)
                .build()
        );

        Order order = Order.builder()
                .id(1L)
                .type(OrderEnum.Type.TO_GO)
                .status(OrderEnum.Status.PENDING)
                .coffees(orderCoffees)
                .build();

        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));

        Order foundOrder = orderService.findById(order.getId());

        assertThat(foundOrder.getId()).isEqualTo(order.getId());
        assertThat(foundOrder.getStatus()).isEqualTo(order.getStatus());
        assertThat(foundOrder.getType()).isEqualTo(order.getType());
        assertThat(foundOrder.getCoffees()).isEqualTo(orderCoffees);
    }

    @Test
    void shouldUpdateOrderStatus() {
        List<Coffee> orderCoffees = List.of(
                Coffee.builder()
                        .id(1L)
                        .name("Espresso")
                        .caffeineGram(8)
                        .brewTime(40)
                        .price(1.60f)
                        .build()
        );
        Order order = Order.builder()
                .id(1L)
                .type(OrderEnum.Type.TO_GO)
                .status(OrderEnum.Status.PENDING)
                .coffees(orderCoffees)
                .build();

        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);

        Order updatedOrder = orderService.updateOrderStatus(order.getId(), OrderEnum.Status.DELIVERED);

        assertThat(updatedOrder.getStatus()).isEqualTo(OrderEnum.Status.DELIVERED);
    }

    @Test
    @Disabled
    void shouldSaveOrder() {
        List<Coffee> orderCoffees = List.of(
                Coffee.builder()
                        .id(1L)
                        .name("Espresso")
                        .caffeineGram(8)
                        .brewTime(40)
                        .price(1.60f)
                        .build()
        );
        Order order = Order.builder()
                .id(1L)
                .type(OrderEnum.Type.TO_GO)
                .status(OrderEnum.Status.PENDING)
                .coffees(orderCoffees)
                .build();
        OrderDTO orderDTO = orderMapper.toOrderDTO(order);

        when(orderRepository.save(order)).thenReturn(order);
        when(orderMapper.toOrderDTO(order)).thenReturn(orderDTO);

//        OrderDTO newOrder = orderService.save(orderDTO);
        //TODO refactor other app parts (Controller & Service)

    }

    @Test
    void shouldReturnCountOfOrdersByStatusIsAndType() {

        when(orderRepository.countOrderByStatusIsAndType(OrderEnum.Status.PENDING, OrderEnum.Type.TO_GO))
                .thenReturn(1);

        assertThat(orderService.countOrderByStatusIsAndType(OrderEnum.Status.PENDING, OrderEnum.Type.TO_GO))
                .isEqualTo(1);
    }
}