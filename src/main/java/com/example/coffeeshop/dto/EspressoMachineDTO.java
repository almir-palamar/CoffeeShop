package com.example.coffeeshop.dto;

public class EspressoMachineDTO {

    private String brand;

    public EspressoMachineDTO(String brand) {
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
