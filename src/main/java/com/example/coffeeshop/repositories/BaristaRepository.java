package com.example.coffeeshop.repositories;

import com.example.coffeeshop.models.Barista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaristaRepository extends JpaRepository<Barista, Long> {

}
