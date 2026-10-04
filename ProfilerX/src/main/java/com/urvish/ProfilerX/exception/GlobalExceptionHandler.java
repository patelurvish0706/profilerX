package com.urvish.ProfilerX.exception;

import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.stream;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleValidationException(MethodArgumentNotValidException exception){

        List<String> errorMessages = new ArrayList<>();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            errorMessages.add(fieldError.getDefaultMessage());
        }

        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleValidationException(RuntimeException exception){

        String errorMessages = exception.getMessage();

        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> preventDuplicateEntry(DataIntegrityViolationException exception){
        String errorMessage = "Data integrity violation occurred";
        String rootCauseMessage = exception.getMostSpecificCause().getMessage();

        if (rootCauseMessage != null) {
            String lowerCause = rootCauseMessage.toLowerCase();
            if (lowerCause.contains("email")) {
                errorMessage = "Email is already registered";
            } else if (lowerCause.contains("username")) {
                errorMessage = "Username is already taken";
            }
        }

        return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);

    }

}
