package com.project.reddital_backend.controllers.requests;

import com.project.reddital_backend.exceptions.BadParametersException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddSubRequestTest {

    @Test
    @DisplayName("test validate good")
    public void validate_good(){
        Request request = AddSubRequest.builder()
                .name("r/AskTal")
                .build();

        request.validate();
    }

    @Test
    @DisplayName("test validate bad name")
    public void validate_name(){

        AddSubRequest request = AddSubRequest.builder()
                .name("")
                .build();

        assertThrows(BadParametersException.class, request::validate);

        request.setName(null);
        assertThrows(BadParametersException.class, request::validate);
    }
}