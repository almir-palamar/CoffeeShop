package com.example.coffeeshop.enums;

public enum OrderEnum {
    INSTANCE; // singleton

    public enum Type {
        TO_GO,
        WEB_UI
    }

    public enum Status {
        PENDING,
        DELIVERED
    }

}
