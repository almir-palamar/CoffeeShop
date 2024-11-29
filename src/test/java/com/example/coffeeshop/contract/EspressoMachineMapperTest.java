package com.example.coffeeshop.contract;

import com.example.coffeeshop.dto.espressoMachine.EspressoMachineDTO;
import com.example.coffeeshop.mappers.EspressoMachineMapper;
import com.example.coffeeshop.models.EspressoMachine;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EspressoMachineMapperTest {

    @InjectMocks
    private EspressoMachineMapper espressoMachineMapper;

    @Test
    void shouldReturnEspressoMachineDTO() {
        EspressoMachine espressoMachine = EspressoMachine.builder()
                .id(1L)
                .brand("Lavazza")
                .grinder(300)
                .build();

        EspressoMachineDTO espressoMachineDTO = espressoMachineMapper.toEspressoMachineDTO(espressoMachine);

        assertThat(espressoMachineDTO.brand()).isEqualTo(espressoMachine.getBrand());
    }
}