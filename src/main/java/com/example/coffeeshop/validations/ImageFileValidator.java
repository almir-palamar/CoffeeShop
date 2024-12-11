package com.example.coffeeshop.validations;

import com.example.coffeeshop.annotations.ValidImageFile;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public class ImageFileValidator implements ConstraintValidator<ValidImageFile, MultipartFile> {

    private long maxSize;
    private static final Set<String> allowedFileTypes = Set.of(".jpg", ".jpeg", ".png");

    @Override
    public void initialize(ValidImageFile constraintAnnotation) {
        this.maxSize = constraintAnnotation.maxSize();
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {

        // allow user to add coffee without image file
        if (file == null || file.getSize() == -1) {
            return true;
        }

        String fileExtension = file.getOriginalFilename()
                                   .substring(file.getOriginalFilename()
                                   .lastIndexOf(".")).toLowerCase();

        boolean isFileTypeValid = allowedFileTypes.contains(fileExtension);

        // remove default violation message
        context.disableDefaultConstraintViolation();

        if (!isFileTypeValid) {
            context.buildConstraintViolationWithTemplate("File " + file.getOriginalFilename() + " does not have valid type")
                    .addConstraintViolation();
        }

        boolean isFileSizeValid = maxSize >= file.getSize();

        if (!isFileSizeValid) {
            context.buildConstraintViolationWithTemplate("File " + file.getOriginalFilename() + " exceed file size limitation")
                    .addConstraintViolation();
        }

        return isFileTypeValid && isFileSizeValid;
    }
}
