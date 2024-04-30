package com.example.coffeeshop.validation;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorResponse {

    private List<Violation> violations;
    private String message;
    private HttpStatus httpStatus;

    public ValidationErrorResponse(List<Violation> violations, String message, HttpStatus httpStatus) {
        this.violations = violations;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public List<Violation> getViolations() {
        return violations;
    }

    public void setViolations(List<Violation> violations) {
        this.violations = violations;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
