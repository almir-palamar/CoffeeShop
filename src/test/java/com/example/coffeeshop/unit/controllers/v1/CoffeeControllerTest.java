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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

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
        int page = 0;
        int pageSize = 10;
        Page<CoffeeDTO> coffeePage = new PageImpl<>(allCoffees);

        when(coffeeService.findAll(page, pageSize)).thenReturn(coffeePage);

        Page<CoffeeDTO> foundCoffees = coffeeController.getCoffees(page, pageSize);

        assertThat(foundCoffees.getSize()).isEqualTo(allCoffees.size());
        assertThat(foundCoffees.getContent().getFirst().name()).isEqualTo(allCoffees.getFirst().name());
        verify(coffeeService).findAll(page, pageSize);
    }
}
