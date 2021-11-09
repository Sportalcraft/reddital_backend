package com.project.reddital_backend.exceptions;

public class UnauthorizedException extends CustomException {

    public UnauthorizedException(String message) {
        super(message);
    }
    public UnauthorizedException() {
        super("you do not have the permissions to do so!");
    }
}
