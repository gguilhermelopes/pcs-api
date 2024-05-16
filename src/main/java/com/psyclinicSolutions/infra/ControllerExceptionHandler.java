package com.psyclinicSolutions.infra;


import com.psyclinicSolutions.infra.exceptions.DataNotFoundException;
import com.psyclinicSolutions.infra.exceptions.DatabaseException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<StandardException> entityNotFound(DataNotFoundException exception, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;

        StandardException error = new StandardException();
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("DataNotFoundException");
        error.setMessage(exception.getMessage());
        error.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationException> argumentNotValid(MethodArgumentNotValidException exception, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ValidationException error = new ValidationException();

        exception.getBindingResult().getAllErrors().forEach((err) -> {
            String fieldName = ((FieldError) err).getField();
            String errorMessage = err.getDefaultMessage();
            error.getErrors().put(fieldName, errorMessage);
        });


        error.setTimestamp(Instant.now());
        error.setStatus(status.value());

        error.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(error);
    }

    public ResponseEntity<StandardException> entityNotFound(DatabaseException exception, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;

        StandardException error = new StandardException();
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("DatabaseException");
        error.setMessage(exception.getMessage());
        error.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(error);
    }

}
