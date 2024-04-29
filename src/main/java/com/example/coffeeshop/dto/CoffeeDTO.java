package com.example.coffeeshop.dto;

public class CoffeeDTO {

    private String name;
    private Integer brewTime;
    private Integer caffeineGram;
    private Float price;

    public CoffeeDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
