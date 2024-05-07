package com.example.coffeeshop.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class CoffeeDTO {

    @NotEmpty
    private String name;
    @NotNull
    private Integer brewTime;
    @NotNull
    private Integer caffeineGram;
    @NotNull
    private Float price;

    public CoffeeDTO(String name, Integer brewTime, Integer caffeineGram, Float price) {
        this.name = name;
        this.brewTime = brewTime;
        this.caffeineGram = caffeineGram;
        this.price = price;
    }

    public CoffeeDTO() {}

    public CoffeeDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBrewTime() {
        return brewTime;
    }

    public void setBrewTime(Integer brewTime) {
        this.brewTime = brewTime;
    }

    public Integer getCaffeineGram() {
        return caffeineGram;
    }

    public void setCaffeineGram(Integer caffeineGram) {
        this.caffeineGram = caffeineGram;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

}
