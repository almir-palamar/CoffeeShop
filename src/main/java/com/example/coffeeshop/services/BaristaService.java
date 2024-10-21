package com.example.coffeeshop.services;

import com.example.coffeeshop.enums.BaristaStatusEnum;
import com.example.coffeeshop.models.Barista;
import com.example.coffeeshop.repositories.BaristaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaristaService {

    private final BaristaRepository baristaRepository;

    @Autowired
    public BaristaService(BaristaRepository baristaRepository) {
        this.baristaRepository = baristaRepository;
    }

    public List<Barista> saveAll(List<Barista> baristas) {
        return this.baristaRepository.saveAll(baristas);
    }

    public List<Barista> findAvailableBaristas() {
        return baristaRepository.findBaristasByStatus(BaristaStatusEnum.AVAILABLE);
    }

    public Barista findBaristasByName(String name) {
        return baristaRepository.findBaristaByName(name);
    }

    public Barista findBaristaById(Long id) {
        return baristaRepository.findById(id).orElse(null);
    }

    public void updateBaristaStatus(Long id, BaristaStatusEnum status) {
        Barista barista = this.findBaristaById(id);
        if (barista != null) {
            barista.setStatus(status);
            this.baristaRepository.save(barista);
        }
    }

    public boolean isThereEnoughCaffeineInGrinder(Barista barista, Integer caffeineNeeded) {
        return barista.getEspressoMachine().getGrinder() >= caffeineNeeded;
    }

}
