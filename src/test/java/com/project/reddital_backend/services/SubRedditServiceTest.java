package com.project.reddital_backend.services;

import com.project.reddital_backend.DTOs.mappers.SubRedditMapper;
import com.project.reddital_backend.DTOs.models.SubRedditDto;
import com.project.reddital_backend.DTOs.models.UserDto;
import com.project.reddital_backend.controllers.requests.AddSubRequest;
import com.project.reddital_backend.exceptions.DuplicateEntityException;
import com.project.reddital_backend.exceptions.EntityNotFoundException;
import com.project.reddital_backend.models.SubReddit;
import com.project.reddital_backend.models.User;
import com.project.reddital_backend.repositories.SubRedditRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
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

        Mockito.when(subMapper.toSubRedditDto((SubReddit) any()))
                .thenReturn(sdto);

        assertEquals(sdto, subServiceUnderTest.findByName(sub.getName()));
    }

    @Test
    void findByName_notFound() {
        Mockito.when(mockSubRedditRepository.findByName(anyString()))
                .thenThrow(EntityNotFoundException.class);


        assertThrows(EntityNotFoundException.class,() -> subServiceUnderTest.findByName(sub.getName()));
    }




    @Test
    @DisplayName("test create with existing subreddit")
    public void create_subExist() {
        Mockito.when(mockSubRedditRepository.findByName(anyString()))
                .thenReturn(sub);

        Mockito.when(subMapper.toSubRedditDto((SubReddit) any()))
                .thenReturn(sdto);

        assertThrows(DuplicateEntityException.class, () -> {
            subServiceUnderTest.create(sdto);
        });
    }

    @Test
    @DisplayName("test create with a new subreddit")
    public void create_newSub() {
        //change to mocking to fut the signup method
        Mockito.when(mockSubRedditRepository.findByName(anyString()))
                .thenReturn(null);

        // return the user that was received
        Mockito.when(mockSubRedditRepository.save(any())).thenAnswer((Answer<SubReddit>) invocation -> {
            Object[] args = invocation.getArguments();
            return (SubReddit) args[0];
        });

        Mockito.when(subMapper.toSubRedditDto((SubReddit) any()))
                .thenReturn(sdto);


        // Run the test
        final SubRedditDto result = subServiceUnderTest.create(sdto);

        // Verify the results
        assertEquals(sub.getName(), result.getName());
    }
}