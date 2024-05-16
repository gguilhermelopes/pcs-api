package com.psyclinicSolutions.infra.exceptions;

public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String message){
        super(message);
    }
}
