package com.example.coffeeshop.services;

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

    public void saveAll(List<Barista> baristas) {
//        return this.baristaRepository.saveAll(baristas);
    }

}
