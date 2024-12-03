package com.example.coffeeshop.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_items")
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
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public OrderItem(Coffee coffee, Integer quantity) {
        this.coffee = coffee;
        this.quantity = quantity;
    }
}
