package com.example.coffeeshop.services;

import com.example.coffeeshop.dto.coffee.CoffeeDTO;
import com.example.coffeeshop.exceptions.EntityNotFoundException;
import com.example.coffeeshop.mappers.CoffeeMapper;
import com.example.coffeeshop.models.Coffee;
import com.example.coffeeshop.repositories.CoffeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CoffeeService {

    private final CoffeeRepository coffeeRepository;
    private final FileUploadService fileUploadService;
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
        Pageable pageable = PageRequest.of(page, size);
        Page<Coffee> coffeePage = coffeeRepository.findAll(pageable);
        return coffeePage.map(coffeeMapper::toCoffeeDTO);
    }

    public CoffeeDTO save(CoffeeDTO coffeeDTO, MultipartFile imageFile) {
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
        Coffee newCoffee = coffeeRepository.save(coffee);
        return coffeeMapper.toCoffeeDTO(newCoffee);
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
