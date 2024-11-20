package com.example.coffeeshop.unit.services;

import com.example.coffeeshop.CoffeeShopApplicationTests;
import com.example.coffeeshop.repositories.CoffeeRepository;
import com.example.coffeeshop.services.CoffeeService;
import com.example.coffeeshop.services.FileUploadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CoffeeServiceTest extends CoffeeShopApplicationTests {

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
    void shouldCallFindByNameInRepository() {
        String coffeeName = "Espresso";

        coffeeService.findByName(coffeeName);

        verify(coffeeRepository).findByName(coffeeName);
    }

    @Test
    void shouldCallFindByIdInRepository() {
        Long id = 1L;

        coffeeService.findById(id);

        verify(coffeeRepository).findById(id);
    }

    @Test
    void shouldCallFindAllInRepository() {
        coffeeService.findAll();

        verify(coffeeRepository).findAll();
    }
}