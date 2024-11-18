package com.example.coffeeshop.repositories;

import com.example.coffeeshop.models.Coffee;
import com.example.coffeeshop.seeders.CoffeeSeeder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Import(CoffeeSeeder.class)
class CoffeeRepositoryTest {

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Test
    void itShouldReturnCoffeeByName() {
        //given => arrange
        String expectedCoffeeName = "Espresso";

        //when => act
        Coffee foundCoffee = coffeeRepository.findByName(expectedCoffeeName).orElse(null);

        //then => assert
        assertThat(foundCoffee).isNotNull();
        assertThat(foundCoffee.getName()).isEqualTo(expectedCoffeeName);
    }

    @Test
    void itShouldReturnCoffeeById() {
        //given => arrange
        long coffeeId = 1L;

        //when => act
        Coffee foundCoffee = coffeeRepository.findById(coffeeId);

        //then => assert
        assertThat(foundCoffee).isNotNull();
        assertThat(foundCoffee.getName()).isEqualTo("Espresso");
    }
}