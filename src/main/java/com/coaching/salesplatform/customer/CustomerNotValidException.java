package com.coaching.salesplatform.customer;

public class CustomerNotValidException extends RuntimeException {
    public CustomerNotValidException(String message) {
        super(message);
    }
}
