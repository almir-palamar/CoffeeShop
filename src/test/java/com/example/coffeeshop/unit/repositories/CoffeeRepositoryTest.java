package com.example.coffeeshop.unit.repositories;

import com.example.coffeeshop.models.Coffee;
import com.example.coffeeshop.repositories.CoffeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class CoffeeRepositoryTest {

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Container
    public static final MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.3.0")
            .withUsername("test")
            .withPassword("test")
            .withDatabaseName("coffeeshop-test");

    @DynamicPropertySource
    static void dynamicConfiguration(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
    }

    @Test
    void shouldReturnCoffeeByName() {
        Coffee espresso = Coffee.builder()
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
    void shouldReturnCoffeeById() {
        Coffee espresso = Coffee.builder()
                .id(1L)
                .name("Espresso")
                .caffeineGram(8)
                .brewTime(40)
                .price(1.60f)
                .build();
        coffeeRepository.saveAndFlush(espresso);

        Optional<Coffee> foundCoffee = coffeeRepository.findById(espresso.getId());

        if (foundCoffee.isPresent()) {
            assertThat(foundCoffee.get()).isNotNull();
            assertThat(foundCoffee.get().getName()).isEqualTo("Espresso");
        }
    }

    @Test
    void shouldReturnAllCoffees() {
        Coffee espresso = Coffee.builder()
                .name("Espresso")
                .caffeineGram(8)
                .brewTime(40)
                .price(1.60f)
                .build();
        coffeeRepository.saveAndFlush(espresso);

        List<Coffee> allCoffees = coffeeRepository.findAll();

        assertThat(allCoffees).isNotNull();
        assertThat(allCoffees.size()).isEqualTo(1);
        assertThat(allCoffees.getFirst().getName()).isEqualTo("Espresso");
    }
}