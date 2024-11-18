package com.example.coffeeshop.services;

import com.example.coffeeshop.exceptions.EntityNotFoundException;
import com.example.coffeeshop.repositories.CoffeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CoffeeServiceTest {

    @Autowired
    private CoffeeService coffeeService;

    @Mock
    private CoffeeRepository coffeeRepository;

    @Mock
    private FileUploadService fileUploadService;

    @BeforeEach
    void setUp() {
        coffeeService = new CoffeeService(coffeeRepository, fileUploadService);
    }

    @Test
    void findCoffeeByNameShouldCallFindByNameInRepository() {
        //arrange
        String expectedCoffee = "Espresso";

        //act
        coffeeService.findByName(expectedCoffee);

        //assert
        //we want to verify that coffeeRepository was invoked during the test
        verify(coffeeRepository).findByName(expectedCoffee);
    }

    @Test
    void willThrowWhenIdNotExists() {
        //arrange
        long idToFind = 1L;

        //act/assert
        assertThatThrownBy(() -> coffeeService.findById(idToFind))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @Disabled
    void findAll() {
    }

    @Test
    @Disabled
    void save() {

        // test if the mocked repository is invoked with the same argument
        // as this service ArgumentCaptor<Coffee>

    }

    @Test
    @Disabled
    void update() {
    }

    @Test
    @Disabled
    void deleteById() {
    }
}