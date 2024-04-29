package com.example.coffeeshop.models;

import jakarta.persistence.*;

@Entity
public class Coffee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true, nullable = false)
    private String name;
    private Integer brewTime;
    private Integer caffeineGram;
    private Float price;

    public Coffee(String name, Integer brewTime, Integer caffeineGram, Float price) {
        this.name = name;
        this.brewTime = brewTime;
        this.caffeineGram = caffeineGram;
        this.price = price;
    }

    public Coffee() {}

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

    @Override
    public String toString() {
        return name + " [" + price + "], " + brewTime + "s [" + caffeineGram + "]";
    }
}
