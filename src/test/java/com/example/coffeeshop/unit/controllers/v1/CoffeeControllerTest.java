package com.example.coffeeshop.unit.controllers.v1;

import com.example.coffeeshop.CoffeeShopApplicationTests;
import com.example.coffeeshop.controllers.v1.CoffeeController;
import com.example.coffeeshop.dto.coffee.CoffeeDTO;
import com.example.coffeeshop.services.CoffeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CoffeeControllerTest extends CoffeeShopApplicationTests {

    @InjectMocks
    private CoffeeController coffeeController;

    @Mock
    private CoffeeService coffeeService;

    @Test
    void shouldReturnCoffeeById() {
        CoffeeDTO espressoDTO = new CoffeeDTO(
                "Espresso",
                40,
                8,
                1.60f
        );

        when(coffeeService.findById(1L)).thenReturn(espressoDTO);

        CoffeeDTO foundCoffee = coffeeController.getCoffee(1L);

        assertThat(foundCoffee.name()).isEqualTo(espressoDTO.name());
        verify(coffeeService).findById(1L);
    }

    @Test
    void shouldReturnAllCoffees() {
        List<CoffeeDTO> allCoffees = List.of(
                new CoffeeDTO(
                        "Espresso",
                        40,
                        8,
                        1.60f
                )
        );

        when(coffeeService.findAll()).thenReturn(allCoffees);

        List<CoffeeDTO> foundCoffees = coffeeController.getCoffees();

        assertThat(foundCoffees.size()).isEqualTo(allCoffees.size());
        assertThat(foundCoffees.getFirst().name()).isEqualTo(allCoffees.getFirst().name());
        verify(coffeeService).findAll();

    }
}
