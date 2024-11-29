package com.example.coffeeshop.contract;

import com.example.coffeeshop.dto.coffee.CoffeeDTO;
import com.example.coffeeshop.mappers.CoffeeMapper;
import com.example.coffeeshop.models.Coffee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class CoffeeMapperTest {

    @InjectMocks
    private CoffeeMapper coffeeMapper;

    @Test
    void shouldReturnCoffeeDTO() {
        Coffee espresso = Coffee.builder()
                .id(1L)
                .name("Espresso")
                .caffeineGram(8)
                .brewTime(40)
                .price(1.60f)
                .build();

        CoffeeDTO espressoDTO = coffeeMapper.toCoffeeDTO(espresso);

        assertThat(espressoDTO.name()).isEqualTo(espresso.getName());
        assertThat(espressoDTO.price()).isEqualTo(espresso.getPrice());
        assertThat(espressoDTO.brewTime()).isEqualTo(espresso.getBrewTime());
        assertThat(espressoDTO.caffeineGram()).isEqualTo(espresso.getCaffeineGram());
    }
}