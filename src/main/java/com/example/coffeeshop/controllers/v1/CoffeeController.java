package com.example.coffeeshop.controllers.v1;

import com.example.coffeeshop.dto.coffee.CoffeeRequest;
import com.example.coffeeshop.exceptions.EntityNotFoundException;
import com.example.coffeeshop.models.Coffee;
import com.example.coffeeshop.services.CoffeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/coffee")
public class CoffeeController {

    private final CoffeeService coffeeService;

    @Autowired
    public CoffeeController(CoffeeService coffeeService) {
        this.coffeeService = coffeeService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Coffee getCoffee(@PathVariable Long id) throws EntityNotFoundException {
        return this.coffeeService.findById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Coffee>> getCoffees() {
        return ResponseEntity.status(HttpStatus.OK).body(this.coffeeService.findAll());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Coffee create(@Valid
                         @RequestBody CoffeeRequest coffeeRequest) {
        return this.coffeeService.save(coffeeRequest, null);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Coffee update(@RequestBody Coffee coffee, @PathVariable Long id) {
        return this.coffeeService.update(coffee, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Coffee delete(@PathVariable Long id) {
        return this.coffeeService.deleteById(id);
    }
}
