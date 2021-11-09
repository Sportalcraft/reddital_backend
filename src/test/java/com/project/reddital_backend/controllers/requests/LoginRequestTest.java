package com.project.reddital_backend.controllers.requests;

import com.project.reddital_backend.exceptions.BadParametersException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class LoginRequestTest {

    // ------------------------------------------------------- properties -------------------------------------------------------


    // ------------------------------------------------------- preparations -------------------------------------------------------


    // ------------------------------------------------------- tests -------------------------------------------------------

    @Test
    @DisplayName("test validate good")
    public void validate_good(){
        Request loginRequestUnderTest = LoginRequest.builder()
                .username("sportalcraft")
                .password("12345678")
                .build();

        loginRequestUnderTest.validate();
    }

    @Test
    @DisplayName("test validate bad username")
    public void validate_badUsername(){

        LoginRequest loginRequestUnderTest = LoginRequest.builder()
                .username("")
                .password("12345678")
                .build();

        assertThrows(BadParametersException.class, loginRequestUnderTest::validate);

        loginRequestUnderTest.setUsername(null);
        assertThrows(BadParametersException.class, loginRequestUnderTest::validate);
    }

    @Test
    @DisplayName("test validate bad password")
    public void validate_badPassword(){

        LoginRequest loginRequestUnderTest = LoginRequest.builder()
                .username("Sportalcraft")
                .password("")
                .build();

        assertThrows(BadParametersException.class, loginRequestUnderTest::validate);

        loginRequestUnderTest.setPassword(null);
        assertThrows(BadParametersException.class, loginRequestUnderTest::validate);
    }
}
