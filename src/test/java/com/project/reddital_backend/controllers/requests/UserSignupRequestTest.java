package com.project.reddital_backend.controllers.requests;

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
    @DisplayName("test clean")
    public void clean() {
        UserSignupRequest userSignupRequestUnderTest = UserSignupRequest.builder()
                .username("     Spo rt alc r\taf\n\nt   ")
                .email("ta l @b a  r\nzi\nl \t ay.yo\tsi")
                .password("  1\n2 3    4\t \n5678")
                .build();

        userSignupRequestUnderTest.clean();

        assertEquals("Sportalcraft", userSignupRequestUnderTest.getUsername());
        assertEquals("tal@barzilay.yosi", userSignupRequestUnderTest.getEmail());
        assertEquals("12345678", userSignupRequestUnderTest.getPassword());
    }

    @Test
    @DisplayName("test validate bad username")
    public void validate_badUsername(){
        UserSignupRequest userSignupRequestUnderTest = UserSignupRequest.builder()
                .username("")
                .email("tal@barzilay.yosi")
                .password("12345678")
                .build();

        assertNotEquals("", userSignupRequestUnderTest.validate());

        userSignupRequestUnderTest.setUsername(null);
        assertNotEquals("", userSignupRequestUnderTest.validate());
    }

    @Test
    @DisplayName("test validate bad password")
    public void validate_badPassword(){
        UserSignupRequest userSignupRequestUnderTest = UserSignupRequest.builder()
                .username("Sportalcraft")
                .email("tal@barzilay.yosi")
                .password("")
                .build();

        assertNotEquals("", userSignupRequestUnderTest.validate());

        userSignupRequestUnderTest.setPassword(null);
        assertNotEquals("", userSignupRequestUnderTest.validate());

        userSignupRequestUnderTest.setPassword("1");
        assertNotEquals("", userSignupRequestUnderTest.validate());

        userSignupRequestUnderTest.setPassword("123");
        assertNotEquals("", userSignupRequestUnderTest.validate());

        userSignupRequestUnderTest.setPassword("12345");
        assertNotEquals("", userSignupRequestUnderTest.validate());
    }

    @Test
    @DisplayName("test validate bad email")
    public void validate_badEmail(){
        UserSignupRequest userSignupRequestUnderTest = UserSignupRequest.builder()
                .username("Sportalcraft")
                .email("")
                .password("12345678")
                .build();

        assertNotEquals("", userSignupRequestUnderTest.validate());

        userSignupRequestUnderTest.setEmail(null);
        assertNotEquals("", userSignupRequestUnderTest.validate());

        userSignupRequestUnderTest.setEmail("abc");
        assertNotEquals("", userSignupRequestUnderTest.validate());

        userSignupRequestUnderTest.setEmail("@");
        assertNotEquals("", userSignupRequestUnderTest.validate());

        userSignupRequestUnderTest.setEmail("a@a");
        assertNotEquals("", userSignupRequestUnderTest.validate());

        userSignupRequestUnderTest.setEmail("a@2.2");
        assertNotEquals("", userSignupRequestUnderTest.validate());
    }

}
