package com.example.coffeeshop.enums;

public enum OrderEnum {
    INSTANCE;

    public enum Type {
        TO_GO,
        WEB_UI
    }

    public enum Status {
        PENDING,
        DELIVERED
    }

}
