package com.example.coffeeshop.unit.repositories;

import com.example.coffeeshop.CoffeeShopApplicationTests;
import com.example.coffeeshop.enums.BaristaStatusEnum;
import com.example.coffeeshop.models.Barista;
import com.example.coffeeshop.repositories.BaristaRepository;
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

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // force DataJpaTest to use MySQLContainer
@Transactional(propagation = Propagation.NOT_SUPPORTED)                      // enables to see changes in db
class BaristaRepositoryTest extends CoffeeShopApplicationTests {

    @Container
    @ServiceConnection
    public static final MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.3.0")
                    .withUsername("user")
                    .withPassword("pass")
                    .withDatabaseName("coffeeShop");

    @Autowired
    private BaristaRepository baristaRepository;

    @Test
    void shouldFindBaristaByStatus() {
        Barista barista = Barista.builder()
                .id(1L)
                .name("John Doe")
                .status(BaristaStatusEnum.AVAILABLE)
                .build();
        baristaRepository.saveAndFlush(barista);
        List<Barista> expectedBaristas = List.of(barista);

        List<Barista> availableBaristas = baristaRepository.findBaristasByStatus(barista.getStatus());

        assertThat(availableBaristas.size()).isEqualTo(expectedBaristas.size());
        assertThat(availableBaristas.getFirst().getName()).isEqualTo(expectedBaristas.getFirst().getName());
        assertThat(availableBaristas.getFirst().getStatus()).isEqualTo(BaristaStatusEnum.AVAILABLE);
    }

    @Test
    void shouldFindBaristaByName() {
        Barista barista = Barista.builder()
                .id(1L)
                .name("John Doe")
                .status(BaristaStatusEnum.AVAILABLE)
                .build();
        baristaRepository.saveAndFlush(barista);

        Barista foundBarista = baristaRepository.findBaristaByName(barista.getName());

        assertThat(barista.getName()).isEqualTo(foundBarista.getName());
        assertThat(barista.getStatus()).isEqualTo(foundBarista.getStatus());
    }
}