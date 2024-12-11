package com.example.coffeeshop.dto.coffee;

import com.example.coffeeshop.annotations.ValidImageFile;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record CreateCoffeeRequest(
        @NotEmpty String name,
        @NotNull Integer brewTime,
        @NotNull Integer caffeineGram,
        @NotNull Float price,
        @ValidImageFile MultipartFile coffeeImage
) {
}
