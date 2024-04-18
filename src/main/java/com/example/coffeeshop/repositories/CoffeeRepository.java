package com.example.coffeeshop.repositories;

import com.example.coffeeshop.models.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoffeeRepository extends JpaRepository<Coffee, Long> {

    Optional<Coffee> findByName (String name);

    Coffee findById(long id);

}
