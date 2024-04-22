package com.example.coffeeshop.seeders;

import com.example.coffeeshop.models.Coffee;
import com.example.coffeeshop.repositories.CoffeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CoffeeSeeder implements CommandLineRunner {

    private final CoffeeRepository coffeeRepository;
    private final List<Coffee> coffeeList = new ArrayList<>();

    @Autowired
    public CoffeeSeeder(CoffeeRepository coffeeRepository) {
        this.coffeeRepository = coffeeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Coffee> coffeeList = List.of(
                Coffee espresso = new Coffee("Espresso", 35, 7, 1.00f),
                Coffee espressoDoppio = new Coffee("Espresso doppio", 45, 14, 2.00f),
                Coffee cappuccino = new Coffee("Cappuccino", 60, 7, 2.50f)
        );
        this.coffeeRepository.saveAll(coffeeList);
    }
}
