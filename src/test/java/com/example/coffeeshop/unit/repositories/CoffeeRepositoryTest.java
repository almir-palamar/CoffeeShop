package com.example.coffeeshop.unit.repositories;

import com.example.coffeeshop.CoffeeShopApplicationTests;
import com.example.coffeeshop.models.Coffee;
import com.example.coffeeshop.repositories.CoffeeRepository;
import com.example.coffeeshop.seeders.CoffeeSeeder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Import(CoffeeSeeder.class)
class CoffeeRepositoryTest extends CoffeeShopApplicationTests{

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Test
    void shouldReturnCoffeeByName() {
        String expectedCoffeeName = "Espresso";

        Coffee foundCoffee = coffeeRepository.findByName(expectedCoffeeName).orElse(null);

        assertThat(foundCoffee).isNotNull();
        assertThat(foundCoffee.getName()).isEqualTo(expectedCoffeeName);
    }

    @Test
    void shouldReturnCoffeeById() {
        Long coffeeId = 1L;

        Optional<Coffee> foundCoffee = coffeeRepository.findById(coffeeId);
        if (foundCoffee.isPresent()) {
            assertThat(foundCoffee.get()).isNotNull();
            assertThat(foundCoffee.get().getName()).isEqualTo("Espresso");
        }
    }

    @Test
    void shouldReturnAllCoffees() {
        List<Coffee> allCoffees = coffeeRepository.findAll();
        assertThat(allCoffees).isNotNull();
    }
}