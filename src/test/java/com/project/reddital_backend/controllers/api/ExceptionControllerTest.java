package com.project.reddital_backend.controllers.api;

import com.project.reddital_backend.exceptions.*;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ExceptionControllerTest {

    @Test
    void handle_DuplicateEntityException() {
        final String err = "err";
        ResponseEntity<String> res = new ExceptionController().handle(new DuplicateEntityException(err));
        assertEquals(res.getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY);
        assertEquals(res.getBody(), err);
    }

    @Test
    void handle_BadParametersException() {
        final String err = "err";
        ResponseEntity<String> res = new ExceptionController().handle(new BadParametersException(err));
        assertEquals(res.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(res.getBody(), err);
    }

    @Test
    void handle_EntityNotFoundException() {
        final String err = "err";
        ResponseEntity<String> res = new ExceptionController().handle(new EntityNotFoundException(err));
        assertEquals(res.getStatusCode(), HttpStatus.NOT_FOUND);
        assertEquals(res.getBody(), err);
    }

    @Test
    void handle_UnauthorizedException() {
        final String err = "err";
        ResponseEntity<String> res = new ExceptionController().handle(new UnauthorizedException(err));
        assertEquals(res.getStatusCode(), HttpStatus.UNAUTHORIZED);
        assertEquals(res.getBody(), err);
    }
}