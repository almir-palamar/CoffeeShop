package com.example.coffeeshop.seeders;

import com.example.coffeeshop.models.Coffee;
import com.example.coffeeshop.repositories.CoffeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@AllArgsConstructor
public class CoffeeSeeder implements CommandLineRunner {

    private final CoffeeRepository coffeeRepository;

    private void seedCoffees() {
        List<Coffee> coffeeList = List.of(
                new Coffee("Espresso", 35, 7, 1.00f, "espresso.jpg"),
                new Coffee("Espresso doppio", 45, 14, 2.00f, "espresso_doppio.jpg"),
                new Coffee("Cappuccino", 60, 7, 2.50f, "cappuccino.jpg")
        );
        this.coffeeRepository.saveAll(coffeeList);
    }

    @Override
    public void run(String... args) throws Exception {
        seedCoffees();
    }
}
