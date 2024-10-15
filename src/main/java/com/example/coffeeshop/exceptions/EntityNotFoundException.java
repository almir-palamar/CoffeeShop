package com.example.coffeeshop.exceptions;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends AppGeneralException{
    public EntityNotFoundException(){
        super(HttpStatus.NOT_FOUND, "resource_not_found");
    }
}