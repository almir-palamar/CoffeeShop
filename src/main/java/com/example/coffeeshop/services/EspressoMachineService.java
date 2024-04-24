package com.example.coffeeshop.services;

import com.example.coffeeshop.models.EspressoMachine;
import com.example.coffeeshop.repositories.EspressoMachineRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EspressoMachineService {

    private final EspressoMachineRepository espressoMachineRepository;

    @Autowired
    public EspressoMachineService(EspressoMachineRepository espressoMachineRepository) {
        this.espressoMachineRepository = espressoMachineRepository;
    }

    public void updateGrinderCoffeeAmount(Integer coffeeAmount, EspressoMachine espressoMachine) {
        espressoMachine.setGrinder(espressoMachine.getGrinder() - coffeeAmount);
        espressoMachineRepository.save(espressoMachine);
    }

    @Transactional
    public void resetEspressoMachines() {
        espressoMachineRepository.findAll().forEach(
                espressoMachine -> espressoMachine.setGrinder(300));
    }

}
