package com.example.coffeeshop.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Barista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name;

//    @OneToMany(mappedBy = "barista")
//    private List<Order> orders;

    @OneToOne
    @JoinColumn(name = "espresso_machine_id")
    private EspressoMachine espressoMachine;

    public Barista(String name, EspressoMachine espressoMachine) {
        this.name = name;
        this.espressoMachine = espressoMachine;
    }

    public Barista() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public List<Order> getOrders() {
//        return orders;
//    }
//
//    public void setOrders(List<Order> orders) {
//        this.orders = orders;
//    }

    public EspressoMachine getEspressoMachine() {
        return espressoMachine;
    }

    public void setEspressoMachine(EspressoMachine espressoMachine) {
        this.espressoMachine = espressoMachine;
    }
}
