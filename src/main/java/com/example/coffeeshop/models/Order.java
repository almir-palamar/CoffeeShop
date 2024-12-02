package com.example.coffeeshop.models;

import com.example.coffeeshop.enums.OrderEnum;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

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
    private List<OrderItem> orderItems;
    @Column(unique = true)
    private String orderNumber;
    @ManyToOne
    @Nullable
    @JoinColumn(name = "barista_id")
    private Barista barista = null;
    @Enumerated(EnumType.STRING)
    private OrderEnum.Type type;
    @Enumerated(EnumType.STRING)
    private OrderEnum.Status status = OrderEnum.Status.PENDING;

    public Order(List<OrderItem> orderItems, OrderEnum.Type type) {
        this.orderItems = orderItems;
        this.type = type;
        this.orderNumber = UUID.randomUUID().toString();
    }

}
