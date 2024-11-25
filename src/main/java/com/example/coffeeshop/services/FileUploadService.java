package com.example.coffeeshop.services;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@NoArgsConstructor
public class FileUploadService {

    private static final Path ROOT_LOCATION = Paths.get("src/main/resources/images");

    public void storeFile(String fileName, MultipartFile file) {
        try {
            Path destinationFile = ROOT_LOCATION.resolve(Paths.get(fileName))
                    .normalize().toAbsolutePath();

            Files.write(destinationFile, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
