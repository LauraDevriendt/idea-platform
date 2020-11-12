package com.coaching.salesplatform.errors;

public class NotValidException extends RuntimeException {
    public NotValidException(String message) {
        super(message);
    }
}
