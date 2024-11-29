package com.example.coffeeshop.contract;

import com.example.coffeeshop.dto.barista.BaristaDTO;
import com.example.coffeeshop.enums.BaristaStatusEnum;
import com.example.coffeeshop.mappers.BaristaMapper;
import com.example.coffeeshop.models.Barista;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class BaristaMapperTest {

    @InjectMocks
    private BaristaMapper baristaMapper;

    @Test
    void shouldReturnBaristaDTO() {
        Barista barista = Barista.builder()
                .id(1L)
                .name("John")
                .status(BaristaStatusEnum.AVAILABLE)
                .build();

        BaristaDTO baristaDTO = baristaMapper.toBaristaDTO(barista);

        assertThat(baristaDTO.name()).isEqualTo(barista.getName());
    }
}