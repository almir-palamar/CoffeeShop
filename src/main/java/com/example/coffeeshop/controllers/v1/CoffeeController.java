package com.example.coffeeshop.controllers.v1;

import com.example.coffeeshop.dto.CoffeeDTO;
import com.example.coffeeshop.models.Coffee;
import com.example.coffeeshop.services.CoffeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public Coffee getCoffee(@PathVariable Long id) {
        return this.coffeeService.findById(id);
    }

    @GetMapping
    public ResponseEntity<List<Coffee>> getCoffees() {
        return ResponseEntity.status(HttpStatus.OK).body(this.coffeeService.findAll());
    }

    @PostMapping
    @PreAuthorize("hasRole('admin')")
    public Coffee create(@Valid
                         @RequestBody CoffeeDTO coffeeDTO) {
        return this.coffeeService.save(coffeeDTO, null);
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
