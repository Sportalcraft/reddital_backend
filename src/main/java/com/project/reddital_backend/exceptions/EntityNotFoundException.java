package com.project.reddital_backend.exceptions;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        super(message);
    }
    public EntityNotFoundException() {
        super("The requested Entity could not be found");
    }
}