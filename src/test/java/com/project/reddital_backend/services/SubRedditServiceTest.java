package com.project.reddital_backend.services;

import com.project.reddital_backend.DTOs.mappers.SubRedditMapper;
import com.project.reddital_backend.DTOs.models.SubRedditDto;
import com.project.reddital_backend.exceptions.EntityNotFoundException;
import com.project.reddital_backend.models.SubReddit;
import com.project.reddital_backend.repositories.SubRedditRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(SpringExtension.class)
class SubRedditServiceTest {

    // ------------------------------------------------ properties ------------------------------------------------

    @MockBean
    private SubRedditRepository mockSubRedditRepository;

    @MockBean
    private SubRedditMapper subMapper;

    @SpyBean
    private SubRedditService subServiceUnderTest;



    private SubReddit sub;
    private SubRedditDto sdto;

    // ------------------------------------------------ preperations ------------------------------------------------

    @BeforeEach
    void setUp() {
        sub = SubReddit.builder()
                .name("r/askTal")
                .build();

        sdto = SubRedditDto.builder()
                .name(sub.getName())
                .build();
    }

    @AfterEach
    void tearDown() {
        sub = null;
        sdto = null;
    }



    // ------------------------------------------------ tests ------------------------------------------------



    @Test
    void findByName() {
        Mockito.when(mockSubRedditRepository.findByName(anyString()))
                .thenReturn(sub);

        Mockito.when(subMapper.toSubRedditDto(any()))
                .thenReturn(sdto);

        assertEquals(sdto, subServiceUnderTest.findByName(sub.getName()));
    }

    @Test
    void findByName_notFound() {
        Mockito.when(mockSubRedditRepository.findByName(anyString()))
                .thenThrow(EntityNotFoundException.class);


        assertThrows(EntityNotFoundException.class,() -> subServiceUnderTest.findByName(sub.getName()));
    }
}