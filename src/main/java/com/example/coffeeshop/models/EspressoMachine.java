package com.example.coffeeshop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "espresso_machines")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class EspressoMachine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private Integer grinder;
    @JsonIgnore
    @OneToOne(mappedBy = "espressoMachine")
    private Barista barista;

    public EspressoMachine(String brand, Integer grinder, Barista barista) {
        this.brand = brand;
        this.grinder = grinder != null ? grinder : 300;
        this.barista = barista;
    }

}
