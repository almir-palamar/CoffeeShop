package com.example.coffeeshop.seeders;

import com.example.coffeeshop.models.EspressoMachine;
import com.example.coffeeshop.repositories.EspressoMachineRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(1)
@AllArgsConstructor
@Profile("dev")
public class EspressoMachineSeeder implements CommandLineRunner {

    private final EspressoMachineRepository espressoMachineRepository;

    private void seedEspressoMachines() {
        List<EspressoMachine> espressoMachines = List.of(
          new EspressoMachine("Lavazza", 300, null),
          new EspressoMachine("Breville Bambino Plus", 300, null),
          new EspressoMachine("Diletta Mio", 300, null)
        );
        espressoMachineRepository.saveAll(espressoMachines);
    }

    @Override
    public void run(String... args) throws Exception {
        seedEspressoMachines();
    }
}
