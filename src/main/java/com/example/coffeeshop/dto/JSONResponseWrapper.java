package com.example.coffeeshop.dto;

public class JSONResponseWrapper {

    private Integer statusCode;
    private String message;
    private Object data;

    public JSONResponseWrapper(Integer statusCode, String message, Object data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public JSONResponseWrapper() {}

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
        return data;
    }

    public void setResult(Object data) {
        this.data = data;
    }

}
