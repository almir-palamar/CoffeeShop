package com.example.coffeeshop.controllers.v1;

import com.example.coffeeshop.models.Coffee;
import com.example.coffeeshop.services.CoffeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coffee")
public class CoffeeController {

    private final CoffeeService coffeeService;

    @Autowired
    public CoffeeController(CoffeeService coffeeService) {
        this.coffeeService = coffeeService;
    }

    @GetMapping("/{id}")
    public Coffee getCoffee(@PathVariable Long id) {
        return this.coffeeService.findById(id);
    }

    @GetMapping
    public List<Coffee> getCoffees() {
        return this.coffeeService.findAll();
    }

    @PostMapping
    public Coffee create(@RequestBody Coffee coffee) {
        return this.coffeeService.save(coffee);
    }

    @PutMapping("/{id}")
    public Coffee update(@RequestBody Coffee coffee, @PathVariable Long id) {
        return this.coffeeService.update(coffee, id);
    }

    @DeleteMapping("/{id}")
    public Coffee delete(@PathVariable Long id) {
        return this.coffeeService.deleteById(id);
    }
}
