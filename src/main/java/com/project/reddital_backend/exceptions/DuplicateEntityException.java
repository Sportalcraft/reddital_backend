package com.project.reddital_backend.exceptions;

public class DuplicateEntityException extends CustomException {

    public DuplicateEntityException(String message) {
        super(message);
    }

    public DuplicateEntityException() {
        super("entity already exists");
    }
}