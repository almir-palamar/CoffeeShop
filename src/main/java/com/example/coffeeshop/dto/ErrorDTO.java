package com.example.coffeeshop.dto;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ErrorDTO extends ResponseDTO {

    private String message = "An error occurred";
    private LocalDateTime timestamp = LocalDateTime.now();

    public ErrorDTO(HttpStatus status) {
        super(status);
        super.setError(true);
    }

    public ErrorDTO(HttpStatus status, String message) {
        super(status, message);
        super.setError(true);
    }

    public ErrorDTO(HttpStatus status, String message, Object data) {
        super(status, message, data);
        super.setError(true);
    }
}
