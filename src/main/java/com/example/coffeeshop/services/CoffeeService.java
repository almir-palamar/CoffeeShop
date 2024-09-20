package com.example.coffeeshop.services;

import com.example.coffeeshop.dto.coffee.CoffeeRequest;
import com.example.coffeeshop.exceptions.EntityNotFoundException;
import com.example.coffeeshop.models.Coffee;
import com.example.coffeeshop.repositories.CoffeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CoffeeService {

    private final CoffeeRepository coffeeRepository;
    private final FileUploadService fileUploadService;

    @Autowired
    public CoffeeService(CoffeeRepository coffeeRepository, FileUploadService fileUploadService) {
        this.coffeeRepository = coffeeRepository;
        this.fileUploadService = fileUploadService;
    }

    public Optional<Coffee> findByName(String name) {
        return this.coffeeRepository.findByName(name);
    }

    public Coffee findById(Long id) throws EntityNotFoundException {
        return this.coffeeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<Coffee> findAll() {
        return this.coffeeRepository.findAll();
    }

    public Coffee save(CoffeeRequest coffeeRequest, MultipartFile imageFile) {
        try {
            String imageName = "no_image.jpg";
            if (imageFile != null && !imageFile.isEmpty()) {
                String imageFileExtension = imageFile.getOriginalFilename().substring(imageFile.getOriginalFilename().lastIndexOf("."));
                imageName = UUID.randomUUID() + "_" + coffeeRequest.getName() + imageFileExtension;
                fileUploadService.storeFile(imageName, imageFile);
            }

            Coffee coffee = new Coffee(
                    coffeeRequest.getName(),
                    coffeeRequest.getBrewTime(),
                    coffeeRequest.getCaffeineGram(),
                    coffeeRequest.getPrice(),
                    imageName
            );

            return this.coffeeRepository.save(coffee);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Coffee update(Coffee coffee, Long id) {
        Optional<Coffee> coffeeOptional = this.coffeeRepository.findById(id);
        if (coffeeOptional.isPresent()) {
            Coffee updatedCoffee = coffeeOptional.get();
            updatedCoffee.setName(coffee.getName());
            updatedCoffee.setPrice(coffee.getPrice());
            updatedCoffee.setBrewTime(coffee.getBrewTime());
            updatedCoffee.setCaffeineGram(coffee.getCaffeineGram());
            return updatedCoffee;
        }
        return null;
    }

    public Coffee deleteById(Long id) {
        Optional<Coffee> coffee = this.coffeeRepository.findById(id);
        if (coffee.isPresent()) {
            this.coffeeRepository.deleteById(id);
            return coffee.get();
        }
        return null;
    }
}
