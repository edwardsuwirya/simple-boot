package com.enigmacamp.hellospring.exception;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

public class ValidationException extends RuntimeException {
    private final BindingResult errors;

    public ValidationException(BindingResult errors) {
        this.errors = errors;
    }

    public String getMessage() {
        List<String> messages = new ArrayList<>();
        for (ObjectError error : errors.getAllErrors()) {
            if (error instanceof FieldError) {
                FieldError fieldError = (FieldError) error;
                String message = fieldError.getDefaultMessage();
                messages.add(message);
            }
        }
        return String.join(",", messages);
    }
}
