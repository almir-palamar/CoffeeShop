package com.example.coffeeshop.services;

import com.example.coffeeshop.enums.BaristaStatusEnum;
import com.example.coffeeshop.models.Barista;
import com.example.coffeeshop.models.Order;
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

    public List<Barista> findBaristasByStatus() {
        return baristaRepository.findBaristasByStatus(BaristaStatusEnum.AVAILABLE);
    }

    public Barista findBaristasByName(String name) {
        return baristaRepository.findBaristaByName(name);
    }

    public void updateBaristaStatus(String name, BaristaStatusEnum status) {
        Barista barista = this.findBaristasByName(name);
        if (barista != null) {
            barista.setStatus(status);
            this.baristaRepository.save(barista);
        }
    }

    // 1. create Scheduled that checks db for new jobs in specified interval
    // if it is implemented as a laravel job
    // the database will always get activated

    public void prepareOrder(Order order, Barista barista) {

    }

//    public void refillGrinder(@NonNull Barista barista) {
//        try {
//            barista.setStatus(BaristaStatusEnum.FILLING_GRINDER);
//            sleep(120000); // pass this as a global property
//            baristaRepository.save(barista);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//            this.interrupt();
//        }
//    }

}
