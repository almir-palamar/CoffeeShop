package com.example.coffeeshop.models;

import com.example.coffeeshop.enums.OrderEnum;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;
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
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;
    @Column(unique = true)
    private String orderNumber;
    private Float total;
    @ManyToOne
    @Nullable
    @JoinColumn(name = "barista_id")
    private Barista barista = null;
    @Enumerated(EnumType.STRING)
    private OrderEnum.Type type;
    @Enumerated(EnumType.STRING)
    private OrderEnum.Status status = OrderEnum.Status.PENDING;

    public Order(List<OrderItem> orderItems, OrderEnum.Type type, String orderNumber, Float total) {
        this.orderItems = orderItems;
        this.type = type;
        this.orderNumber = orderNumber;
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderNumber, order.orderNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(orderNumber);
    }
}
