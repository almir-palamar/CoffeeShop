package com.example.coffeeshop.annotations;

import com.example.coffeeshop.validations.ImageFileValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ImageFileValidator.class)
public @interface ValidImageFile {

    String message() default "Corrupted file!";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    long maxSize() default 3 * 1024; // Default: 3kB

}
