package com.project.reddital_backend.controllers.requests;

import com.project.reddital_backend.exceptions.BadParametersException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserSignupRequestTest {

    // ------------------------------------------------------- properties -------------------------------------------------------


    // ------------------------------------------------------- preparations -------------------------------------------------------


    // ------------------------------------------------------- tests -------------------------------------------------------

    @Test
    @DisplayName("test validate bad username")
    public void validate_badUsername(){

        UserSignupRequest userSignupRequestUnderTest = UserSignupRequest.builder()
                .username("")
                .email("tal@barzilay.yosi")
                .password("12345678")
                .build();

        assertThrows(BadParametersException.class, userSignupRequestUnderTest::validate);

        userSignupRequestUnderTest.setUsername(null);
        assertThrows(BadParametersException.class, userSignupRequestUnderTest::validate);
    }

    @Test
    @DisplayName("test validate bad password")
    public void validate_badPassword(){

        UserSignupRequest userSignupRequestUnderTest = UserSignupRequest.builder()
                .username("Sportalcraft")
                .email("tal@barzilay.yosi")
                .password("")
                .build();

        assertThrows(BadParametersException.class, userSignupRequestUnderTest::validate);

        userSignupRequestUnderTest.setPassword(null);
        assertThrows(BadParametersException.class, userSignupRequestUnderTest::validate);

        userSignupRequestUnderTest.setPassword("1");
        assertThrows(BadParametersException.class, userSignupRequestUnderTest::validate);

        userSignupRequestUnderTest.setPassword("123");
        assertThrows(BadParametersException.class, userSignupRequestUnderTest::validate);

        userSignupRequestUnderTest.setPassword("12345");
        assertThrows(BadParametersException.class, userSignupRequestUnderTest::validate);
    }

    @Test
    @DisplayName("test validate bad email")
    public void validate_badEmail(){

        UserSignupRequest userSignupRequestUnderTest = UserSignupRequest.builder()
                .username("Sportalcraft")
                .email("")
                .password("12345678")
                .build();

        assertThrows(BadParametersException.class, userSignupRequestUnderTest::validate);

        userSignupRequestUnderTest.setEmail(null);
        assertThrows(BadParametersException.class, userSignupRequestUnderTest::validate);

        userSignupRequestUnderTest.setEmail("abc");
        assertThrows(BadParametersException.class, userSignupRequestUnderTest::validate);

        userSignupRequestUnderTest.setEmail("@");
        assertThrows(BadParametersException.class, userSignupRequestUnderTest::validate);
    }

}
