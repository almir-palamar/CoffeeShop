package com.example.coffeeshop.unit.controllers.v1;

import com.example.coffeeshop.CoffeeShopApplicationTests;
import com.example.coffeeshop.controllers.v1.CoffeeController;
import com.example.coffeeshop.models.Coffee;
import com.example.coffeeshop.services.CoffeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CoffeeControllerTest extends CoffeeShopApplicationTests {

    @Autowired
    private CoffeeController coffeeController;

    @Mock
    private CoffeeService coffeeService;

    @BeforeEach
    void setup() {
        coffeeController = new CoffeeController(coffeeService);
    }

    @Test
    void shouldReturnCoffeeById() {
        Long id = 1L;

        when(coffeeService.findById(id)).thenReturn(Optional.of(new Coffee()));

        coffeeController.getCoffee(id);

        verify(coffeeService).findById(id);
    }
}
