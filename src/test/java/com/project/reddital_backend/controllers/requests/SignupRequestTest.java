package com.project.reddital_backend.controllers.requests;

import com.project.reddital_backend.exceptions.BadParametersException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class SignupRequestTest {

    // ------------------------------------------------------- properties -------------------------------------------------------


    // ------------------------------------------------------- preparations -------------------------------------------------------


    // ------------------------------------------------------- tests -------------------------------------------------------

    @Test
    @DisplayName("test validate good")
    public void validate_good(){
        SignupRequest signupRequestUnderTest = SignupRequest.builder()
                .username("Sportalcraft")
                .email("tal@barzilay.yosi")
                .password("12345678")
                .build();

        signupRequestUnderTest.validate();
    }

    @Test
    @DisplayName("test validate bad username")
    public void validate_badUsername(){

        SignupRequest signupRequestUnderTest = SignupRequest.builder()
                .username("")
                .email("tal@barzilay.yosi")
                .password("12345678")
                .build();

        assertThrows(BadParametersException.class, signupRequestUnderTest::validate);

        signupRequestUnderTest.setUsername(null);
        assertThrows(BadParametersException.class, signupRequestUnderTest::validate);
    }

    @Test
    @DisplayName("test validate bad password")
    public void validate_badPassword(){

        SignupRequest signupRequestUnderTest = SignupRequest.builder()
                .username("Sportalcraft")
                .email("tal@barzilay.yosi")
                .password("")
                .build();

        assertThrows(BadParametersException.class, signupRequestUnderTest::validate);

        signupRequestUnderTest.setPassword(null);
        assertThrows(BadParametersException.class, signupRequestUnderTest::validate);

        signupRequestUnderTest.setPassword("1");
        assertThrows(BadParametersException.class, signupRequestUnderTest::validate);

        signupRequestUnderTest.setPassword("123");
        assertThrows(BadParametersException.class, signupRequestUnderTest::validate);

        signupRequestUnderTest.setPassword("12345");
        assertThrows(BadParametersException.class, signupRequestUnderTest::validate);
    }

    @Test
    @DisplayName("test validate bad email")
    public void validate_badEmail(){

        SignupRequest signupRequestUnderTest = SignupRequest.builder()
                .username("Sportalcraft")
                .email("")
                .password("12345678")
                .build();

        assertThrows(BadParametersException.class, signupRequestUnderTest::validate);

        signupRequestUnderTest.setEmail(null);
        assertThrows(BadParametersException.class, signupRequestUnderTest::validate);

        signupRequestUnderTest.setEmail("abc");
        assertThrows(BadParametersException.class, signupRequestUnderTest::validate);

        signupRequestUnderTest.setEmail("@");
        assertThrows(BadParametersException.class, signupRequestUnderTest::validate);
    }

}
