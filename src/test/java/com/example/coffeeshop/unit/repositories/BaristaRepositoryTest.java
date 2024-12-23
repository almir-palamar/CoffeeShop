package com.example.coffeeshop.unit.repositories;

import com.example.coffeeshop.enums.BaristaStatusEnum;
import com.example.coffeeshop.models.Barista;
import com.example.coffeeshop.repositories.BaristaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class BaristaRepositoryTest extends RepositoryBaseTest {

    @Autowired
    private BaristaRepository baristaRepository;

    @Test
    void shouldFindBaristaByStatus() {
        Barista barista = Barista.builder()
                .id(1L)
                .name("John Doe")
                .status(BaristaStatusEnum.AVAILABLE)
                .build();
        baristaRepository.saveAndFlush(barista);
        List<Barista> expectedBaristas = List.of(barista);

        List<Barista> availableBaristas = baristaRepository.findBaristasByStatus(barista.getStatus());

        assertThat(availableBaristas.size()).isEqualTo(expectedBaristas.size());
        assertThat(availableBaristas.getFirst().getName()).isEqualTo(expectedBaristas.getFirst().getName());
        assertThat(availableBaristas.getFirst().getStatus()).isEqualTo(BaristaStatusEnum.AVAILABLE);
    }

    @Test
    void shouldFindBaristaByName() {
        Barista barista = Barista.builder()
                .id(1L)
                .name("John Doe")
                .status(BaristaStatusEnum.AVAILABLE)
                .build();
        baristaRepository.saveAndFlush(barista);

        Barista foundBarista = baristaRepository.findBaristaByName(barista.getName());

        assertThat(barista.getName()).isEqualTo(foundBarista.getName());
        assertThat(barista.getStatus()).isEqualTo(foundBarista.getStatus());
    }
}