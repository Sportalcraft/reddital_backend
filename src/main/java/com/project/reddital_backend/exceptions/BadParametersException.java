package com.project.reddital_backend.exceptions;

public class BadParametersException extends CustomException {

    public BadParametersException(String message) {
        super(message);
    }
    public BadParametersException() {
        super("The requested parameters are wrong!");
    }
}
