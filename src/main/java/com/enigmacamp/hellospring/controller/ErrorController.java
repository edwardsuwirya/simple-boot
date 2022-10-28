package com.enigmacamp.hellospring.controller;

import com.enigmacamp.hellospring.exception.EntityExistException;
import com.enigmacamp.hellospring.exception.NotFoundException;
import com.enigmacamp.hellospring.exception.UnauthorizedException;
import com.enigmacamp.hellospring.exception.ValidationException;
import com.enigmacamp.hellospring.model.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(ValidationException.class)
    ResponseEntity<ErrorResponse> handleValidationException(ValidationException exception, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ErrorResponse("X07", exception.getMessage()));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    ResponseEntity<ErrorResponse> handleEntityExistViolationException(MaxUploadSizeExceededException exception, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ErrorResponse("X07", "File size is too big"));
    }

    @ExceptionHandler(EntityExistException.class)
    ResponseEntity<ErrorResponse> handleEntityExistViolationException(EntityExistException exception, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ErrorResponse("X03", exception.getMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException exception, HttpServletRequest request) {
        Set<ConstraintViolation<?>> violation = exception.getConstraintViolations();
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation error : violation) {
            errors.add(error.getMessage());
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("X02", errors.toString()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception, HttpServletRequest request
    ) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        List<String> errors = new ArrayList<>();
        for (FieldError error : fieldErrors) {
            errors.add(error.getDefaultMessage());
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("X04", errors.toString()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDataNotFoundException(
            NotFoundException exception, HttpServletRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("X01", exception.getMessage()));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(
            UnauthorizedException exception, HttpServletRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse("X05", exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllException(
            Exception exception, HttpServletRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("X06", exception.getMessage()));
    }
}
