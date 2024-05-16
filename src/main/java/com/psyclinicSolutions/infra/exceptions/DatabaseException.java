package com.psyclinicSolutions.infra.exceptions;

public class DatabaseException extends RuntimeException {
    public DatabaseException(String message){
        super(message);
    }
}
