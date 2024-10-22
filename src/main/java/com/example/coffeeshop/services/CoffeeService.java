package com.example.coffeeshop.services;

import com.example.coffeeshop.dto.coffee.CoffeeDTO;
import com.example.coffeeshop.exceptions.EntityNotFoundException;
import com.example.coffeeshop.models.Coffee;
import com.example.coffeeshop.repositories.CoffeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private Logger logger = LoggerFactory.getLogger(CoffeeService.class);

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

    public Coffee save(CoffeeDTO coffeeDTO, MultipartFile imageFile) {
        try {
            String imageName = "no_image.jpg";
            if (imageFile != null && !imageFile.isEmpty()) {
                String imageFileExtension = imageFile.getOriginalFilename().substring(imageFile.getOriginalFilename().lastIndexOf("."));
                imageName = UUID.randomUUID() + "_" + coffeeDTO.name() + imageFileExtension;
                fileUploadService.storeFile(imageName, imageFile);
            }

            Coffee coffee = new Coffee(
                    coffeeDTO.name(),
                    coffeeDTO.brewTime(),
                    coffeeDTO.caffeineGram(),
                    coffeeDTO.price(),
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
            this.coffeeRepository.save(updatedCoffee);
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
