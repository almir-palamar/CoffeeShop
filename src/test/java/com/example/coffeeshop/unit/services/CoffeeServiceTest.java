package com.example.coffeeshop.unit.services;

import com.example.coffeeshop.CoffeeShopApplicationTests;
import com.example.coffeeshop.dto.coffee.CoffeeDTO;
import com.example.coffeeshop.mappers.CoffeeMapper;
import com.example.coffeeshop.models.Coffee;
import com.example.coffeeshop.repositories.CoffeeRepository;
import com.example.coffeeshop.services.CoffeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CoffeeServiceTest extends CoffeeShopApplicationTests {

    @InjectMocks
    private CoffeeService coffeeService;

    @Mock
    private CoffeeRepository coffeeRepository;

    @Mock
    private CoffeeMapper coffeeMapper;

    @Test
    void shouldReturnCoffeeByName() {
        Coffee espresso = Coffee.builder()
                .id(1L)
                .name("Espresso")
                .caffeineGram(8)
                .brewTime(40)
                .price(1.60f)
                .build();

        when(coffeeRepository.findByName(espresso.getName())).thenReturn(Optional.of(espresso));
        when(coffeeMapper.toCoffeeDTO(espresso)).thenReturn(
                new CoffeeDTO(
                        espresso.getName(),
                        espresso.getBrewTime(),
                        espresso.getCaffeineGram(),
                        espresso.getPrice()
                )
        );

        CoffeeDTO foundCoffee = coffeeService.findByName(espresso.getName());
        assertThat(foundCoffee.name()).isEqualTo(espresso.getName());
        verify(coffeeRepository).findByName(espresso.getName());
    }

    @Test
    void shouldReturnCoffeeById() {
        Coffee espresso = Coffee.builder()
                .id(1L)
                .name("Espresso")
                .caffeineGram(8)
                .brewTime(40)
                .price(1.60f)
                .build();

        when(coffeeRepository.findById(espresso.getId())).thenReturn(Optional.of(espresso));
        when(coffeeMapper.toCoffeeDTO(espresso)).thenReturn(
                new CoffeeDTO(
                        espresso.getName(),
                        espresso.getBrewTime(),
                        espresso.getCaffeineGram(),
                        espresso.getPrice()
                )
        );

        CoffeeDTO foundCoffee = coffeeService.findById(espresso.getId());

        assertThat(foundCoffee.name()).isEqualTo(espresso.getName());
        verify(coffeeRepository).findById(espresso.getId());
    }

    @Test
    void shouldReturnAllCoffees() {
        Coffee espresso = Coffee.builder()
                .id(1L)
                .name("Espresso")
                .caffeineGram(8)
                .brewTime(40)
                .price(1.60f)
                .build();
        List<Coffee> allCoffees = List.of(espresso);

        when(coffeeRepository.findAll()).thenReturn(allCoffees);
        when(coffeeMapper.toCoffeeDTO(any(Coffee.class))).thenReturn(
                new CoffeeDTO(
                        espresso.getName(),
                        espresso.getBrewTime(),
                        espresso.getCaffeineGram(),
                        espresso.getPrice()
                )
        );

        List<CoffeeDTO> expectedCoffees = coffeeService.findAll();

        assertThat(expectedCoffees.size()).isEqualTo(allCoffees.size());
        assertThat(expectedCoffees.getFirst().name()).isEqualTo(allCoffees.getFirst().getName());
        verify(coffeeRepository).findAll();
    }

    @Test
    void shouldAddAndReturnNewCoffee() {
        CoffeeDTO espressoDTO = new CoffeeDTO(
                "Espresso",
                40,
                8,
                1.60f
        );

        Coffee espresso = new Coffee(
                "Espresso",
                40,
                8,
                1.60f,
                null
        );

        when(coffeeRepository.save(espresso)).thenReturn(espresso);
        when(coffeeMapper.toCoffeeDTO(espresso)).thenReturn(
                espressoDTO
        );

        CoffeeDTO newCoffeeDTO = coffeeService.save(espressoDTO, null);

        assertThat(newCoffeeDTO.name()).isEqualTo(espresso.getName());

        ArgumentCaptor<Coffee> coffeeArgumentCaptor = ArgumentCaptor.forClass(Coffee.class);

        verify(coffeeRepository).save(coffeeArgumentCaptor.capture());

        Coffee capturedCoffee = coffeeArgumentCaptor.getValue();

        assertThat(capturedCoffee).isEqualTo(espresso);
    }
}