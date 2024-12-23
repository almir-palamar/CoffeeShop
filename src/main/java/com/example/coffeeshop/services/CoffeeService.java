package com.example.coffeeshop.services;

import com.example.coffeeshop.dto.coffee.CoffeeDTO;
import com.example.coffeeshop.dto.coffee.CreateCoffeeRequest;
import com.example.coffeeshop.dto.coffee.UpdateCoffeeRequest;
import com.example.coffeeshop.exceptions.EntityNotFoundException;
import com.example.coffeeshop.mappers.CoffeeMapper;
import com.example.coffeeshop.models.Coffee;
import com.example.coffeeshop.repositories.CoffeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoffeeService {

    private final CoffeeRepository coffeeRepository;
    private final FileService fileService;
    private final CoffeeMapper coffeeMapper;

    public CoffeeDTO findByName(String name) {
        Coffee coffee = coffeeRepository.findByName(name).orElseThrow(EntityNotFoundException::new);
        return coffeeMapper.toCoffeeDTO(coffee);
    }

    public CoffeeDTO findById(Long id) {
        Coffee coffee = coffeeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return coffeeMapper.toCoffeeDTO(coffee);
    }

    public Page<CoffeeDTO> findAll(Integer page, Integer size) {
        Page<Coffee> coffeePage = coffeeRepository.findAll(PageRequest.of(page, size));
        return coffeePage.map(coffeeMapper::toCoffeeDTO);
    }

    public CoffeeDTO save(CreateCoffeeRequest createCoffeeRequest) {
        String filename = fileService.generateFileName(createCoffeeRequest.coffeeImage());
        if (filename != null) {
            fileService.storeFile(filename, createCoffeeRequest.coffeeImage());
        }
        Coffee coffee = new Coffee(
                createCoffeeRequest.name(),
                createCoffeeRequest.brewTime(),
                createCoffeeRequest.caffeineGram(),
                createCoffeeRequest.price(),
                filename
        );
        Coffee newCoffee = coffeeRepository.save(coffee);
        return coffeeMapper.toCoffeeDTO(newCoffee);
    }

    public CoffeeDTO update(UpdateCoffeeRequest updateCoffeeRequest, Long id) {
        Coffee updateCoffee = coffeeRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        //TODO if deployed on AWS and S3Bucket when upload
        // if it have the same key it will be replaced so maybe
        // this logic of FileService should be scoped to dev profile

        String newFilename = fileService.generateFileName(updateCoffeeRequest.coffeeImage());
        if (newFilename.equalsIgnoreCase(updateCoffee.getImagePath())) {
            fileService.updateFile(updateCoffee.getImagePath(), newFilename,updateCoffeeRequest.coffeeImage());
        }
        updateCoffee.setName(updateCoffeeRequest.name());
        updateCoffee.setBrewTime(updateCoffeeRequest.brewTime());
        updateCoffee.setCaffeineGram(updateCoffeeRequest.caffeineGram());
        updateCoffee.setPrice(updateCoffeeRequest.price());
        updateCoffee.setImagePath(newFilename);

        coffeeRepository.save(updateCoffee);
        return coffeeMapper.toCoffeeDTO(updateCoffee);
    }

    public CoffeeDTO deleteById(Long id) {
        Coffee deleteCoffee = coffeeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        fileService.deleteFile(deleteCoffee.getImagePath());
        coffeeRepository.delete(deleteCoffee);
        return coffeeMapper.toCoffeeDTO(deleteCoffee);
    }
}
