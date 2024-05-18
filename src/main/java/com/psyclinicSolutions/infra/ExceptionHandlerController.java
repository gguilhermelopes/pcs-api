package com.psyclinicSolutions.infra;


import com.psyclinicSolutions.infra.exceptions.DataNotFoundException;
import com.psyclinicSolutions.infra.exceptions.DatabaseException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;

@ControllerAdvice
public class ExceptionHandlerController {
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

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandardException> databaseError(DatabaseException exception, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;

        StandardException error = new StandardException();
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("DatabaseException");
        error.setMessage(exception.getMessage());
        error.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<StandardException> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        StandardException error = new StandardException();
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("HttpMessageNotReadableException");
        error.setMessage("Id inválido.");
        error.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<StandardException> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String invalidId = request.getRequestURI().split("/")[2];

        StandardException error = new StandardException();
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("MethodArgumentTypeMismatchException");
        error.setMessage(String.format("Id %s inválido.", invalidId));
        error.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(error);
    }
}
