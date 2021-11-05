package com.project.reddital_backend.exceptions;

public class DuplicateEntityException extends RuntimeException {

    public DuplicateEntityException(String message) {
        super(message);
    }

    public DuplicateEntityException() {
        super("entity already exists");
    }
}