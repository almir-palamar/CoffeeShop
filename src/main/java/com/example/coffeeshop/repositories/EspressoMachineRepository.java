package com.example.coffeeshop.repositories;

import com.example.coffeeshop.models.EspressoMachine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EspressoMachineRepository extends JpaRepository<EspressoMachine, Long> {
}
