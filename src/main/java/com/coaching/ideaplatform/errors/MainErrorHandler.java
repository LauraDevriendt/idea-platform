package com.coaching.ideaplatform.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class MainErrorHandler {
    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<ErrorMessage> NotFoundExceptionHandler(NotFoundException e) {
        return new ResponseEntity<>(new ErrorMessage( HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotValidException.class)
    ResponseEntity<ErrorMessage> NotValidExceptionHandler(NotValidException e) {
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    ResponseEntity<ErrorMessage> NotValidExceptionHandler() {
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.METHOD_NOT_ALLOWED.value(), "You can't use this method. This is forbidden"),HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ValidationErrorMessage> MethodArgumentNotValidexceptionHandler(MethodArgumentNotValidException e) {
        List<FieldError> fielderrors = e.getBindingResult().getFieldErrors();
        List<ErrorFieldMessage> errorFieldMessages = fielderrors
                .stream()
                .map(fieldError -> new ErrorFieldMessage(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(new ValidationErrorMessage(HttpStatus.BAD_REQUEST.value(),errorFieldMessages), HttpStatus.BAD_REQUEST);
    }


}
