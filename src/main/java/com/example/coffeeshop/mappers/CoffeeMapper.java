package com.example.coffeeshop.mappers;

import com.example.coffeeshop.dto.coffee.CoffeeDTO;
import com.example.coffeeshop.models.Coffee;
import org.springframework.stereotype.Component;

@Component
public class CoffeeMapper {

    public CoffeeDTO toCoffeeDTO(Coffee coffee) {
        return new CoffeeDTO(
                coffee.getName(),
                coffee.getBrewTime(),
                coffee.getCaffeineGram(),
                coffee.getPrice(),
                coffee.getImagePath()
        );
    }

}
