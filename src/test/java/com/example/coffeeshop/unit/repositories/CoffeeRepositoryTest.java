package com.example.coffeeshop.unit.repositories;

import com.example.coffeeshop.models.Coffee;
import com.example.coffeeshop.repositories.CoffeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CoffeeRepositoryTest extends RepositoryBaseTest {

    @Autowired
    private CoffeeRepository coffeeRepository;

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
        int page = 0;
        int pageSize = 10;

        Page<Coffee> allCoffees = coffeeRepository.findAll(PageRequest.of(page, pageSize));

        assertThat(allCoffees).isNotNull();
        assertEquals(1, allCoffees.getTotalPages());
        assertEquals(1, allCoffees.getContent().size());
        assertThat(allCoffees.getContent().getFirst().getName()).isEqualTo(espresso.getName());
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