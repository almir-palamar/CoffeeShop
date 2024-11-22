package com.example.coffeeshop.unit.repositories;

import com.example.coffeeshop.CoffeeShopApplicationTests;
import com.example.coffeeshop.models.Coffee;
import com.example.coffeeshop.repositories.CoffeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // force DataJpaTest to use MySQLContainer
@Transactional(propagation = Propagation.NOT_SUPPORTED)                      // enables to see changes in db
@Rollback                                                                    // add manual rollback after each test as the @Transactional is disabled
class CoffeeRepositoryTest extends CoffeeShopApplicationTests {

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Container
    @ServiceConnection
    public static final MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.3.0")
            .withUsername("user")
            .withPassword("pass")
            .withDatabaseName("coffeeshop");

    @Test
    void shouldReturnCoffeeById() {
        Coffee espresso = Coffee.builder()
                .id(1L)
                .name("Espresso")
                .caffeineGram(8)
                .brewTime(40)
                .price(1.60f)
                .build();
        coffeeRepository.saveAndFlush(espresso);

        Coffee foundCoffee = coffeeRepository.findById(espresso.getId()).orElse(null);

        assertThat(foundCoffee).isNotNull();
        assertThat(foundCoffee.getName()).isEqualTo(espresso.getName());
    }

    @Test
    void shouldReturnCoffeeByName() {
        Coffee espresso = Coffee.builder()
                .id(1L)
                .name("Espresso")
                .caffeineGram(8)
                .brewTime(40)
                .price(1.60f)
                .build();
        coffeeRepository.saveAndFlush(espresso);

        Coffee foundCoffee = coffeeRepository.findByName(espresso.getName()).orElse(null);

        assertThat(foundCoffee).isNotNull();
        assertThat(foundCoffee.getName()).isEqualTo(espresso.getName());
    }

    @Test
    void shouldReturnAllCoffees() {
        Coffee espresso = Coffee.builder()
                .id(1L)
                .name("Espresso")
                .caffeineGram(8)
                .brewTime(40)
                .price(1.60f)
                .build();
        coffeeRepository.saveAndFlush(espresso);

        List<Coffee> allCoffees = coffeeRepository.findAll();

        assertThat(allCoffees).isNotNull();
        assertEquals(1, allCoffees.size());
        assertThat(allCoffees.getFirst().getName()).isEqualTo(espresso.getName());
    }

    @Test
    void shouldAddAndReturnNewCoffee() {
        Coffee espresso = Coffee.builder()
                .id(1L)
                .name("Espresso")
                .caffeineGram(8)
                .brewTime(40)
                .price(1.60f)
                .build();
        coffeeRepository.saveAndFlush(espresso);

        Coffee newCoffee = coffeeRepository.saveAndFlush(espresso);

        assertThat(newCoffee).isNotNull();
        assertThat(newCoffee.getName()).isEqualTo(espresso.getName());
    }
}