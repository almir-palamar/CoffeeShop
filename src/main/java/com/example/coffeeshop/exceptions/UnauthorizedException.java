package com.example.coffeeshop.exceptions;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends AppGeneralException{
    public UnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
