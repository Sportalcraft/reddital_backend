package com.project.reddital_backend.services;

import com.project.reddital_backend.DTOs.models.UserDto;
import com.project.reddital_backend.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(SpringExtension.class)
class AuthenticationServiceTest {

    // ------------------------------------------------ properties ------------------------------------------------

    @MockBean
    private UserService mockUserService;

    @SpyBean
    private AuthenticationService authServiceUnderTest;



    private UserDto user;

    // ------------------------------------------------ preperations ------------------------------------------------

    @BeforeEach
    void setUp() {
        user = UserDto.builder().username("name").build();

        Mockito.when(mockUserService.findUserById(anyLong()))
                .thenReturn(user);

    }

    @AfterEach
    void tearDown() {
        user = null;
    }



    // ------------------------------------------------ tests ------------------------------------------------

    @Test
    void authenticate() {
        assertEquals(authServiceUnderTest.authenticate("42"), user);
    }
}