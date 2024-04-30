package com.example.coffeeshop.exceptions;

public class MoreThan5PendingOrdersToGoException extends RuntimeException {

    public MoreThan5PendingOrdersToGoException(String message) {
        super(message);
    }

}
