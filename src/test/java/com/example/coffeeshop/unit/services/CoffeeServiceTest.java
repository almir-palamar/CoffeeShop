package com.example.coffeeshop.unit.services;

import com.example.coffeeshop.CoffeeShopApplicationTests;
import com.example.coffeeshop.dto.coffee.CoffeeDTO;
import com.example.coffeeshop.mappers.CoffeeMapper;
import com.example.coffeeshop.models.Coffee;
import com.example.coffeeshop.repositories.CoffeeRepository;
import com.example.coffeeshop.services.CoffeeService;
import com.example.coffeeshop.services.FileUploadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CoffeeServiceTest extends CoffeeShopApplicationTests {

    @Autowired
    private static CoffeeService coffeeService;

    @Mock
    private CoffeeRepository coffeeRepository;

    @Mock
    private FileUploadService fileUploadService;

    @Mock
    private CoffeeMapper coffeeMapper;

    @Mock
    private MultipartFile multipartFile;

    @BeforeEach
    void setUp() {
        coffeeService = new CoffeeService(coffeeRepository, fileUploadService, coffeeMapper);
    }

    @Test
    void shouldReturnCoffeeByName() {
        Coffee espresso = Coffee.builder()
                .id(1L)
                .name("Espresso")
                .caffeineGram(8)
                .brewTime(40)
                .price(1.60f)
                .build();

        when(coffeeRepository.findByName(espresso.getName())).thenReturn(Optional.ofNullable(espresso));
        when(coffeeMapper.apply(espresso)).thenReturn(
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
        when(coffeeMapper.apply(espresso)).thenReturn(
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
        when(coffeeMapper.apply(any(Coffee.class))).thenReturn(
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

        Coffee espresso = new Coffee("Espresso", 40, 8, 1.60f, null);
        M

        when(coffeeRepository.save(any(Coffee.class))).thenReturn(espresso);
        when(coffeeMapper.apply(espresso)).thenReturn(
                new CoffeeDTO(
                        espresso.getName(),
                        espresso.getBrewTime(),
                        espresso.getCaffeineGram(),
                        espresso.getPrice()
                )
        );

        CoffeeDTO newCoffeeDTO = coffeeService.save(espressoDTO, null);

        assertThat(newCoffeeDTO.name()).isEqualTo(espresso.getName());
        verify(coffeeRepository).save(espresso);
    }
}