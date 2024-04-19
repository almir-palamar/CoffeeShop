package com.example.coffeeshop.models;

import jakarta.persistence.*;

@Entity
public class EspressoMachine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String brand;
    private Integer grinder;
    @OneToOne(mappedBy = "espressoMachine")
    private Barista barista;

    public EspressoMachine(String brand, Integer grinder, Barista barista) {
        this.brand = brand;
        this.grinder = grinder;
        this.barista = barista;
    }

    public EspressoMachine() {
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getGrinder() {
        return grinder;
    }

    public void setGrinder(Integer grinder) {
        this.grinder = grinder;
    }

    public Barista getBarista() {
        return barista;
    }

    public void setBarista(Barista barista) {
        this.barista = barista;
    }
}
