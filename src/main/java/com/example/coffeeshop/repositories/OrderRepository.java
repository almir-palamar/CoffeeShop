package com.example.coffeeshop.repositories;

import com.example.coffeeshop.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

//    public Order findByOrderId(Long Id);

}