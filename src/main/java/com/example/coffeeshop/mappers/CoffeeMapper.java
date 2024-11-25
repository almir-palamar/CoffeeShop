package com.example.coffeeshop.mappers;

import com.example.coffeeshop.dto.coffee.CoffeeDTO;
import com.example.coffeeshop.models.Coffee;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CoffeeMapper implements Function<Coffee, CoffeeDTO> {

    @Override
    public CoffeeDTO apply(Coffee coffee) {
        return new CoffeeDTO(
                coffee.getName(),
                coffee.getBrewTime(),
                coffee.getCaffeineGram(),
                coffee.getPrice()
        );
    }

}
