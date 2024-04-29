package com.example.coffeeshop.mappers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.ModelMap;

@Configuration
public class CoffeeMapper {

    ModelMap modelMap = new ModelMap();

    @Bean
    public ModelMap coffeeModelMap() {
        ModelMap modelMap = new ModelMap();
        return modelMap;
    }


}
