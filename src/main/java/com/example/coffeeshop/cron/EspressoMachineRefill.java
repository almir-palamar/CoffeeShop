package com.example.coffeeshop.cron;

import com.example.coffeeshop.services.EspressoMachineService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@AllArgsConstructor
public class EspressoMachineRefill {

    private final EspressoMachineService espressoMachineService;

    @Scheduled(cron = "${cron.shop-opening-time}")
    public void espressoMachineRefill() {
        this.espressoMachineService.resetEspressoMachines();
    }

}
