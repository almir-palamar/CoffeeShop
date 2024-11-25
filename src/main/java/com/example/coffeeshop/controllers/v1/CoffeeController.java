package com.example.coffeeshop.controllers.v1;

import com.example.coffeeshop.dto.coffee.CoffeeDTO;
import com.example.coffeeshop.models.Coffee;
import com.example.coffeeshop.services.CoffeeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/coffees")
@Tag(name = "Coffees")
@AllArgsConstructor
public class CoffeeController {

    private final CoffeeService coffeeService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CoffeeDTO getCoffee(@PathVariable Long id) {
        return coffeeService.findById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CoffeeDTO> getCoffees() {
        return coffeeService.findAll();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirement(name = "JWTAuth")
    public CoffeeDTO create(@Valid
                         @RequestBody CoffeeDTO coffeeDTO) {
        return coffeeService.save(coffeeDTO, null);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = "JWTAuth")
    public Coffee update(@RequestBody Coffee coffee, @PathVariable Long id) {
        return coffeeService.update(coffee, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    @SecurityRequirement(name = "JWTAuth")
    public Coffee delete(@PathVariable Long id) {
        return coffeeService.deleteById(id);
    }
}
