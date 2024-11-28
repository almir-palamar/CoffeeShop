package com.example.coffeeshop.mappers;

import com.example.coffeeshop.dto.barista.BaristaDTO;
import com.example.coffeeshop.models.Barista;
import org.springframework.stereotype.Component;

@Component
public class BaristaMapper {

    public BaristaDTO toBaristaDTO(Barista barista) {
        return new BaristaDTO(
                barista.getName()
        );
    }

}
