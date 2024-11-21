package com.example.coffeeshop.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class Violation {

    private String fieldName;
    private String message;

}
