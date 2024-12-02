package com.example.coffeeshop.repositories;

import com.example.coffeeshop.enums.OrderEnum;
import com.example.coffeeshop.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Integer countOrderByStatusIsAndType(OrderEnum.Status status, OrderEnum.Type type);

    Optional<Order> findById(Long id);
}
