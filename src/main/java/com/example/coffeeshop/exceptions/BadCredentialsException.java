package com.example.coffeeshop.exceptions;

import org.springframework.http.HttpStatus;

public class BadCredentialsException extends AppGeneralException{
    public BadCredentialsException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
