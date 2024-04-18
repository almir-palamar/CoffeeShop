package com.example.coffeeshop.models;

import com.example.coffeeshop.enums.OrderEnum;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

////    @ManyToMany
////    private List<Coffee> coffees;
//
////    @ManyToOne
////    @Nullable
////    @JoinColumn(name = "barista_id")
////    private Barista barista;
//
////    @Enumerated(EnumType.STRING)
//    private String type;
//
////    @Enumerated(EnumType.STRING)
//    private String status;
//
//    public Order(
////            List<Coffee> coffees,
//            String type,
//            String status
////            Barista barista
//    ) {
////        this.coffees = coffees;
//        this.type = type;
//        this.status = status;
////        this.barista = barista;
//    }
//
//    public Order() {}
//
////    public List<Coffee> getCoffees() {
////        return coffees;
////    }
//
////    public void setCoffees(List<Coffee> coffees) {
////        this.coffees = coffees;
////    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
////    public Barista getBarista() {
////        return barista;
////    }
////
////    public void setBarista(Barista barista) {
////        this.barista = barista;
////    }
//
//    @Override
//    public String toString() {
//        return "Order{" +
//                "id=" + Id +
////                ", coffees=" + coffees +
////                ", barista=" + barista +
//                ", status=" + status +
//                ", type=" + type +
//                '}';
//    }
}
