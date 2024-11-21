package com.example.coffeeshop.models;

import com.example.coffeeshop.enums.BaristaStatusEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "baristas")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
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

}
