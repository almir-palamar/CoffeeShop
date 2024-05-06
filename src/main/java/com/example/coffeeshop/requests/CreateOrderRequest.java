package com.example.coffeeshop.requests;

import com.example.coffeeshop.enums.OrderEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class CreateOrderRequest {

    @NotEmpty(message = "Please pick at least one coffee...")
    private List<String> coffees;
    @Enumerated(value = EnumType.STRING)
    private OrderEnum.Type type = OrderEnum.Type.WEB_UI;

    public CreateOrderRequest(List<String> coffees) {
        this.coffees = coffees;
    }

    public CreateOrderRequest() {}

    public List<String> getCoffees() {
        return coffees;
    }

    public void setCoffees(List<String> coffees) {
        this.coffees = coffees;
    }

    public OrderEnum.Type getType() {
        return type;
    }

    public void setType(OrderEnum.Type type) {
        this.type = type;
    }
}
