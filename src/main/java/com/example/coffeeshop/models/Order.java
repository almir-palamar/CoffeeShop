package com.example.coffeeshop.models;

import com.example.coffeeshop.enums.OrderEnum;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany
    private List<Coffee> coffees;
    @ManyToOne
    @Nullable
    @JoinColumn(name = "barista_id")
    private Barista barista = null;
    @Enumerated(EnumType.STRING)
    private OrderEnum.Type type;
    @Enumerated(EnumType.STRING)
    private OrderEnum.Status status = OrderEnum.Status.PENDING;

    public Order(List<Coffee> coffees, OrderEnum.Type type) {
        this.coffees = coffees;
        this.type = type;
    }

}
