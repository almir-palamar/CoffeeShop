package com.example.coffeeshop.exceptions;

import org.springframework.http.HttpStatus;

public class MoreThan5PendingOrdersToGoException extends AppGeneralException {

    public MoreThan5PendingOrdersToGoException() {
        super(HttpStatus.BAD_REQUEST, "more_than_5_pending_orders_to_go");
    }

}
