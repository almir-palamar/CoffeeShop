package com.example.coffeeshop.models;

import com.example.coffeeshop.enums.OrderEnum;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToMany
    private List<Coffee> coffees;

    @ManyToOne
    @Nullable
    @JoinColumn(name = "barista_id")
    private Barista barista;

    @Enumerated(EnumType.STRING)
    private OrderEnum.Type type;

    @Enumerated(EnumType.STRING)
    private OrderEnum.Status status;

    public Order(List<Coffee> coffees, OrderEnum.Type type, OrderEnum.Status status, @Nullable Barista barista) {
        this.coffees = coffees;
        this.type = type;
        this.status = status;
        this.barista = barista;
    }

    public Order() {
    }

    public List<Coffee> getCoffees() {
        return coffees;
    }

    public void setCoffees(List<Coffee> coffees) {
        this.coffees = coffees;
    }

    public OrderEnum.Type getType() {
        return type;
    }

    public void setType(OrderEnum.Type type) {
        this.type = type;
    }

    public OrderEnum.Status getStatus() {
        return status;
    }

    public void setStatus(OrderEnum.Status status) {
        this.status = status;
    }

    public Barista getBarista() {
        return barista;
    }

    public void setBarista(Barista barista) {
        this.barista = barista;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + Id +
                ", coffees=" + coffees +
                ", barista=" + barista +
                ", status=" + status +
                ", type=" + type +
                '}';
    }
}
