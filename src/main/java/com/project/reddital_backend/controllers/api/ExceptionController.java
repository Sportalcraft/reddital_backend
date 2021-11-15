package com.project.reddital_backend.controllers.api;

import com.project.reddital_backend.exceptions.BadParametersException;
import com.project.reddital_backend.exceptions.DuplicateEntityException;
import com.project.reddital_backend.exceptions.EntityNotFoundException;
import com.project.reddital_backend.exceptions.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
@CrossOrigin
public class ExceptionController {

    //The goal of this controller is to handle exceptions

    // -------------------------------------- exception handlers --------------------------------------

    @ExceptionHandler(DuplicateEntityException.class)
    public final ResponseEntity<String> handle(DuplicateEntityException ex) {
        return ResponseEntity.unprocessableEntity()
                .body(ex.getMessage());
    }

    @ExceptionHandler(BadParametersException.class)
    public final ResponseEntity<String> handle(BadParametersException ex) {
        return ResponseEntity.badRequest()
                .body(ex.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public final ResponseEntity<String> handle(EntityNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public final ResponseEntity<String> handle(UnauthorizedException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ex.getMessage());
    }
}
