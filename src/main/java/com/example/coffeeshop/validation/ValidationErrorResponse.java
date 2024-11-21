package com.example.coffeeshop.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class ValidationErrorResponse {

    private List<Violation> violations;
    private String message;
    private HttpStatus httpStatus;

}
