package com.coaching.ideaplatform.errors;

public class NotValidException extends RuntimeException {
    public NotValidException(String message) {
        super(message);
    }
}
