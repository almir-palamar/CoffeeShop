package com.example.coffeeshop.repositories;

import com.example.coffeeshop.enums.BaristaStatusEnum;
import com.example.coffeeshop.models.Barista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaristaRepository extends JpaRepository<Barista, Long> {

    List<Barista> findBaristasByStatus(BaristaStatusEnum baristaStatus);

    Barista findBaristaByName(String baristaName);

}
