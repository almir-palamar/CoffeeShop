package com.example.coffeeshop.services;

import com.example.coffeeshop.models.EspressoMachine;
import com.example.coffeeshop.repositories.EspressoMachineRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EspressoMachineService {

    private final EspressoMachineRepository espressoMachineRepository;

    public void updateGrinderCoffeeAmount(Integer coffeeAmount, EspressoMachine espressoMachine) {
        espressoMachine.setGrinder(espressoMachine.getGrinder() - coffeeAmount);
        espressoMachineRepository.save(espressoMachine);
    }

    @Transactional
    public void resetEspressoMachines() {
        espressoMachineRepository.findAll().forEach(
                espressoMachine -> espressoMachine.setGrinder(300));
    }

    public void refillGrinder(Long id) {
        EspressoMachine espressoMachine = espressoMachineRepository.findById(id).orElseThrow();
        espressoMachine.setGrinder(300);
    }

    //expose grinders capacity as a global variable as well as time needed to refill grinder

}
