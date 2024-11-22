package com.example.coffeeshop.repositories;

import com.example.coffeeshop.models.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoffeeRepository extends JpaRepository<Coffee, Long> {

    Optional<Coffee> findByName (String name);

    Optional<Coffee> findById (Long id);

}
