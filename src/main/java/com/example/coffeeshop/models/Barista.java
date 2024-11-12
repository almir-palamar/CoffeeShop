package com.example.coffeeshop.models;

import com.example.coffeeshop.enums.BaristaStatusEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "baristas")
public class Barista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToOne
    @JoinColumn(name = "espresso_machine_id")
    private EspressoMachine espressoMachine;
    @Enumerated(EnumType.STRING)
    private BaristaStatusEnum status;

    public Barista(String name, EspressoMachine espressoMachine) {
        this.name = name;
        this.espressoMachine = espressoMachine;
        this.status = BaristaStatusEnum.AVAILABLE;
    }

    public Barista() {
    }

    public Long getId() {return id;}

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

    public BaristaStatusEnum getStatus() {
        return status;
    }

    public void setStatus(BaristaStatusEnum status) {
        this.status = status;
    }

}
