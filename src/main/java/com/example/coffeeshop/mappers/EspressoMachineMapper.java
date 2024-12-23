package com.example.coffeeshop.mappers;

import com.example.coffeeshop.dto.espressoMachine.EspressoMachineDTO;
import com.example.coffeeshop.models.EspressoMachine;
import org.springframework.stereotype.Component;

@Component
public class EspressoMachineMapper {

    public EspressoMachineDTO toEspressoMachineDTO(EspressoMachine espressoMachine) {
        return new EspressoMachineDTO(espressoMachine.getBrand());
    }

}
