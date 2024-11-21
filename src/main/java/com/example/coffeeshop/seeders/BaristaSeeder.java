package com.example.coffeeshop.seeders;

import com.example.coffeeshop.models.Barista;
import com.example.coffeeshop.models.EspressoMachine;
import com.example.coffeeshop.repositories.BaristaRepository;
import com.example.coffeeshop.repositories.EspressoMachineRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Order(2)
@AllArgsConstructor
public class BaristaSeeder implements CommandLineRunner {

    private final BaristaRepository baristaRepository;
    private final EspressoMachineRepository espressoMachineRepository;

    private void seedBaristas() {
        Optional<EspressoMachine> type1 = espressoMachineRepository.findById(1L);
        Optional<EspressoMachine> type2 = espressoMachineRepository.findById(2L);
        Optional<EspressoMachine> type3 = espressoMachineRepository.findById(3L);
        List<Barista> baristas = List.of(
                new Barista("John", type1.get()),
                new Barista("Doe", type2.get()),
                new Barista("Steve", type3.get())
        );
        baristaRepository.saveAll(baristas);
    }

    @Override
    public void run(String... args) throws Exception {
        seedBaristas();
    }
}
