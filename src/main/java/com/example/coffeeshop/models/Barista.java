package com.example.coffeeshop.models;

import jakarta.persistence.*;

@Entity
public class Barista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name;
    @OneToOne
    @JoinColumn(name = "espresso_machine_id")
    private EspressoMachine espressoMachine;

    public Barista(String name, EspressoMachine espressoMachine) {
        this.name = name;
        this.espressoMachine = espressoMachine;
    }

    public Barista() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EspressoMachine getEspressoMachine() {
        return espressoMachine;
    }

    public void setEspressoMachine(EspressoMachine espressoMachine) {
        this.espressoMachine = espressoMachine;
    }

}
