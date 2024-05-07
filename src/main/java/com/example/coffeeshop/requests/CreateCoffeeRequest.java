package com.example.coffeeshop.requests;

public class CreateCoffeeRequest {

    private String name;
    private Integer brewTime;
    private Integer caffeineGram;
    private Float price;
    private String imagePath;

    public CreateCoffeeRequest(String name, Integer brewTime, Integer caffeineGram, Float price, String imagePath) {
        this.name = name;
        this.brewTime = brewTime;
        this.caffeineGram = caffeineGram;
        this.price = price;
        this.imagePath = imagePath;
    }

    public CreateCoffeeRequest() {}

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getCaffeineGram() {
        return caffeineGram;
    }

    public void setCaffeineGram(Integer caffeineGram) {
        this.caffeineGram = caffeineGram;
    }

    public Integer getBrewTime() {
        return brewTime;
    }

    public void setBrewTime(Integer brewTime) {
        this.brewTime = brewTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
