package com.project.reddital_backend.exceptions;

public class EntityNotFoundException extends CustomException {

    public EntityNotFoundException(String message) {
        super(message);
    }
    public EntityNotFoundException() {
        super("The requested Entity could not be found");
    }
}