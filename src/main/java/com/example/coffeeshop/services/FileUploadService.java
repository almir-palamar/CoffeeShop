package com.example.coffeeshop.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileUploadService {

    private static final Path ROOT_LOCATION = Paths.get("src/main/resources/images");

    public FileUploadService() {}

    public void storeFile(String fileName, MultipartFile file) throws IOException {
        Path destinationFile = ROOT_LOCATION.resolve(Paths.get(fileName))
                .normalize().toAbsolutePath();

        Files.write(destinationFile, file.getBytes());
    }

}
