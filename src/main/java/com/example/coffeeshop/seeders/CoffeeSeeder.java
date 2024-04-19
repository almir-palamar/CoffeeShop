package com.example.coffeeshop.seeders;

import com.example.coffeeshop.models.Coffee;
import com.example.coffeeshop.repositories.CoffeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CoffeeSeeder {

    private final CoffeeRepository coffeeRepository;
    private final List <Coffee> coffeeList = new ArrayList<>();

    @Autowired
    public CoffeeSeeder(CoffeeRepository coffeeRepository) {
        this.coffeeRepository = coffeeRepository;
        this.seed();
    }

    public void seed() {

        Coffee espresso = new Coffee("Espresso", 35, 7, 1.00f);
        Coffee espressoDoppio = new Coffee("Espresso doppio", 45, 14, 2.00f);
        Coffee cappuccino = new Coffee("Cappuccino", 60, 7, 2.50f);

        coffeeList.add(espresso);
        coffeeList.add(espressoDoppio);
        coffeeList.add(cappuccino);

        this.coffeeRepository.saveAll(coffeeList);

    }

}
