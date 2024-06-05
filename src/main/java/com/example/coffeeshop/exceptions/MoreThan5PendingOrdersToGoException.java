package com.example.coffeeshop.exceptions;

import org.springframework.http.HttpStatus;

public class MoreThan5PendingOrdersToGoException extends AppGeneralException {

    public MoreThan5PendingOrdersToGoException() {
        super(HttpStatus.BAD_REQUEST, "More than 5 pending orders to go");
    }

}
