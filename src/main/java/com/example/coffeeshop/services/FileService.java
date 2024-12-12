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
public class FileService {

    private static final Path ROOT_LOCATION = Paths.get("src/main/resources/images");

    public void storeFile(String fileName, MultipartFile file) {
        try {
            Path destinationFile = ROOT_LOCATION.resolve(Paths.get(fileName))
                    .normalize().toAbsolutePath();

            Files.write(destinationFile, file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    public void deleteFile(String filename) {
        try {
            Path destinationFile = ROOT_LOCATION.resolve(Paths.get(filename))
                    .normalize().toAbsolutePath();

            Files.deleteIfExists(destinationFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateFile(String oldFileName, String newFileName, MultipartFile newFile) {
        try {
            Path oldFileDestination = ROOT_LOCATION.resolve(Paths.get(oldFileName))
                    .normalize().toAbsolutePath();
            Files.deleteIfExists(oldFileDestination);

            Path newFileDestination = ROOT_LOCATION.resolve(Paths.get(newFileName))
                    .normalize().toAbsolutePath();

            Files.write(newFileDestination, newFile.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateFileName(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        return file.getName().toLowerCase();
    }

}
