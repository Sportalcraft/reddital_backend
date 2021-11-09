package com.project.reddital_backend.controllers.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response<T> {//extends ResponseEntity<T> {

/*    // ------------------------------------------------- properties -------------------------------------------------

    //private Status status;
    //private T payload;
    //private String msg;
    //private Date time = new Date();

    // ---------------------------------------------- ctors ----------------------------------------------

    public Response(HttpStatus status) {
        super(status);
    }

    public Response(T body, HttpStatus status) {
        super(body, status);
    }





    // ------------------------------------------------- static initializers -------------------------------------------------


    public static <T> Response<T> ok() {
        return new Response<>(HttpStatus.OK);
    }


    public static <T> Response<T> badRequest() {
        return new Response<>(HttpStatus.BAD_REQUEST);
    }


    public static <T> Response<T> badRequest() {
        return new Response<>(HttpStatus.OK);;
    }

    public static <T> Response<T> unauthorized() {
        Response<T> response = new Response<>();
        response.setStatus(Status.UNAUTHORIZED);
        return response;
    }

    public static <T> Response<T> validationException() {
        Response<T> response = new Response<>();
        response.setStatus(Status.VALIDATION_EXCEPTION);
        return response;
    }

    public static <T> Response<T> wrongCredentials() {
        Response<T> response = new Response<>();
        response.setStatus(Status.WRONG_CREDENTIALS);
        return response;
    }

    public static <T> Response<T> accessDenied() {
        Response<T> response = new Response<>();
        response.setStatus(Status.ACCESS_DENIED);
        return response;
    }

    public static <T> Response<T> internal() {
        Response<T> response = new Response<>();
        response.setStatus(Status.INTERNAL_ERROR);
        return response;
    }

    public static <T> Response<T> notFound() {
        Response<T> response = new Response<>();
        response.setStatus(Status.NOT_FOUND);
        return response;
    }

    public static <T> Response<T> duplicateEntity() {
        Response<T> response = new Response<>();
        response.setStatus(Status.DUPLICATE_ENTITY);
        return response;
    }*/

}
