package com.example.coffeeshop.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "coffee_id")
    private Coffee coffee;
    private Integer quantity;
    @OneToMany
    private Order order;

    public OrderItem(Coffee coffee, Integer quantity) {
        this.coffee = coffee;
        this.quantity = quantity;
    }
}
