package com.example.coffeeshop.unit.services;

import com.example.coffeeshop.dto.coffee.CoffeeDTO;
import com.example.coffeeshop.dto.coffee.CoffeeOrderItemDTO;
import com.example.coffeeshop.dto.order.CreateOrderRequest;
import com.example.coffeeshop.dto.order.OrderDTO;
import com.example.coffeeshop.dto.order.OrderItemDTO;
import com.example.coffeeshop.enums.OrderEnum;
import com.example.coffeeshop.mappers.OrderMapper;
import com.example.coffeeshop.models.Coffee;
import com.example.coffeeshop.models.Order;
import com.example.coffeeshop.models.OrderItem;
import com.example.coffeeshop.repositories.CoffeeRepository;
import com.example.coffeeshop.repositories.OrderItemRepository;
import com.example.coffeeshop.repositories.OrderRepository;
import com.example.coffeeshop.services.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Mock
    private CoffeeRepository coffeeRepository;

    @Mock
    private OrderMapper orderMapper;

    @Test
    void shouldFindOrderById() {
        Coffee coffee = Coffee.builder()
                .id(1L)
                .name("Espresso")
                .caffeineGram(8)
                .brewTime(40)
                .price(1.60f)
                .build();

        List<OrderItem> orderItems = List.of(
                OrderItem.builder()
                        .coffee(coffee)
                        .quantity(1)
                        .build()
        );

        CoffeeOrderItemDTO coffeeOrderItemDTO = new CoffeeOrderItemDTO(
                coffee.getName(),
                coffee.getBrewTime(),
                coffee.getCaffeineGram(),
                coffee.getPrice(),
                1
        );

        String orderNumber = UUID.randomUUID().toString();

        Order order = Order.builder()
                .id(1L)
                .orderNumber(orderNumber)
                .type(OrderEnum.Type.TO_GO)
                .status(OrderEnum.Status.PENDING)
                .orderItems(orderItems)
                .total(1.60f)
                .build();

        OrderDTO orderDTO = new OrderDTO(
                List.of(coffeeOrderItemDTO),
                orderNumber,
                1.60f,
                OrderEnum.Type.TO_GO,
                OrderEnum.Status.PENDING
        );

        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        when(orderMapper.toOrderDTO(order)).thenReturn(orderDTO);

        OrderDTO foundOrder = orderService.findById(order.getId());

        assertThat(foundOrder.orderNumber()).isEqualTo(order.getOrderNumber());
        assertThat(foundOrder.orderTotal()).isEqualTo(order.getTotal());
        assertThat(foundOrder.orderItems().size()).isEqualTo(order.getOrderItems().size());
        assertThat(foundOrder.status()).isEqualTo(order.getStatus());
        assertThat(foundOrder.type()).isEqualTo(order.getType());
        assertThat(foundOrder.orderItems().getFirst().quantity()).isEqualTo(order.getOrderItems().getFirst().getQuantity());
        assertThat(foundOrder.orderItems().getFirst().name()).isEqualTo(order.getOrderItems().getFirst().getCoffee().getName());
    }

    @Test
    void shouldUpdateOrderStatus() {
        Coffee coffee = Coffee.builder()
                .id(1L)
                .name("Espresso")
                .caffeineGram(8)
                .brewTime(40)
                .price(1.60f)
                .build();

        List<OrderItem> orderItems = List.of(
                OrderItem.builder()
                        .coffee(coffee)
                        .quantity(1)
                        .build()
        );

        String orderNumber = UUID.randomUUID().toString();

        Order order = Order.builder()
                .id(1L)
                .orderNumber(orderNumber)
                .type(OrderEnum.Type.TO_GO)
                .status(OrderEnum.Status.PENDING)
                .orderItems(orderItems)
                .total(1.60f)
                .build();

        CoffeeOrderItemDTO coffeeOrderItemDTO = new CoffeeOrderItemDTO(
                coffee.getName(),
                coffee.getBrewTime(),
                coffee.getCaffeineGram(),
                coffee.getPrice(),
                1
        );

        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(
                Order.builder()
                        .status(OrderEnum.Status.DELIVERED)
                        .build()
        );
        when(orderMapper.toOrderDTO(order)).thenReturn(
                new OrderDTO(
                        List.of(coffeeOrderItemDTO),
                        order.getOrderNumber(),
                        order.getTotal(),
                        order.getType(),
                        OrderEnum.Status.DELIVERED
                )
        );

        OrderDTO updatedOrder = orderService.updateOrderStatus(order.getId(), OrderEnum.Status.DELIVERED);

        assertThat(updatedOrder.status()).isEqualTo(OrderEnum.Status.DELIVERED);
        verify(orderRepository).findById(order.getId());
        verify(orderRepository).save(order);
    }

    @Test
    void shouldSaveOrder() {
        CoffeeDTO coffeeDTO = new CoffeeDTO(
                "Espresso",
                40,
                8,
                1.60f,
                null
        );

        Coffee coffee = Coffee.builder()
                .id(1L)
                .name("Espresso")
                .caffeineGram(8)
                .brewTime(40)
                .price(1.60f)
                .build();

        OrderItemDTO orderItemDTO = new OrderItemDTO(coffeeDTO, 1);

        CoffeeOrderItemDTO coffeeOrderItemDTO = new CoffeeOrderItemDTO(
                coffeeDTO.name(),
                coffeeDTO.brewTime(),
                coffeeDTO.caffeineGram(),
                coffeeDTO.price(),
                1
        );

        List<OrderItem> orderItems = List.of(
                OrderItem.builder()
                        .coffee(coffee)
                        .quantity(1)
                        .build()
        );

        Order order = Order.builder()
                .orderItems(orderItems)
                .total(1.60f)
                .type(OrderEnum.Type.TO_GO)
                .status(OrderEnum.Status.PENDING)
                .build();

        CreateOrderRequest createOrderRequest = new CreateOrderRequest(
                List.of(orderItemDTO),
                OrderEnum.Type.TO_GO
        );

        when(coffeeRepository.findByName("Espresso")).thenReturn(Optional.of(coffee));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderMapper.toOrderDTO(order)).thenReturn(
                new OrderDTO(
                        List.of(coffeeOrderItemDTO),
                        order.getOrderNumber(),
                        order.getTotal(),
                        order.getType(),
                        order.getStatus()
                )
        );

        OrderDTO newOrderDTO = orderService.save(createOrderRequest);

        assertThat(newOrderDTO.orderNumber()).isEqualTo(order.getOrderNumber());

        ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.forClass(Order.class);

        verify(orderRepository).save(orderArgumentCaptor.capture());

        Order capturedOrder = orderArgumentCaptor.getValue();

        assertThat(capturedOrder.getOrderItems().size()).isEqualTo(order.getOrderItems().size());
        assertThat(capturedOrder.getStatus()).isEqualTo(order.getStatus());
        assertThat(capturedOrder.getType()).isEqualTo(order.getType());
        assertThat(capturedOrder.getOrderItems().getFirst().getQuantity()).isEqualTo(order.getOrderItems().getFirst().getQuantity());
        assertThat(capturedOrder.getOrderItems().getFirst().getCoffee().getName()).isEqualTo(order.getOrderItems().getFirst().getCoffee().getName());
    }

    @Test
    void shouldReturnCountOfOrdersByStatusIsAndType() {

        when(orderRepository.countOrderByStatusIsAndType(OrderEnum.Status.PENDING, OrderEnum.Type.TO_GO))
                .thenReturn(1);

        assertThat(orderService.countOrderByStatusIsAndType(OrderEnum.Status.PENDING, OrderEnum.Type.TO_GO))
                .isEqualTo(1);
    }
}