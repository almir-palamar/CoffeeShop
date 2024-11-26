package com.example.coffeeshop.unit.repositories;

import com.example.coffeeshop.CoffeeShopApplicationTests;
import com.example.coffeeshop.enums.OrderEnum;
import com.example.coffeeshop.models.Order;
import com.example.coffeeshop.repositories.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class OrderRepositoryTest extends CoffeeShopApplicationTests {

    @Autowired
    private OrderRepository orderRepository;

    @Container
    @ServiceConnection
    private static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.3.0")
            .withUsername("test")
            .withPassword("test")
            .withDatabaseName("coffeeShop");

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