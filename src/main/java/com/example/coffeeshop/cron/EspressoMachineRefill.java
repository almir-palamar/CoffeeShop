package com.example.coffeeshop.cron;

import com.example.coffeeshop.services.EspressoMachineService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class EspressoMachineRefill {

    private final EspressoMachineService espressoMachineService;

    public EspressoMachineRefill(EspressoMachineService espressoMachineService) {
        this.espressoMachineService = espressoMachineService;
    }

    @Scheduled(cron = "${cron.shop-opening-time}")
    public void espressoMachineRefill() {
        this.espressoMachineService.resetEspressoMachines();
    }

}
