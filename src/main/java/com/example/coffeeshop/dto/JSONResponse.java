package com.example.coffeeshop.dto;

import jakarta.servlet.http.HttpServletResponseWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;

public class JSONResponse {

    private Integer statusCode;
    private String message;
    private Object result;

    public JSONResponse(Integer statusCode, String message, Object result) {
        this.statusCode = statusCode;
        this.message = message;
        this.result = result;
    }

    public Integer getStatus() {
        return statusCode;
    }

    public void setStatus(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

}
