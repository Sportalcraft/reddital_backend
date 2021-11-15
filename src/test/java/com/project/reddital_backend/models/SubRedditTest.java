package com.project.reddital_backend.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class SubRedditTest {
    // ------------------------------------------------------- properties -------------------------------------------------------

    final String name      = "r/askTal";


    // ------------------------------------------------------- tests -------------------------------------------------------

    @Test
    @DisplayName("test creating new user")
    public void buildUser() {

        SubReddit sub = SubReddit.builder().name(name).build();

        assertEquals(name, sub.getName());
    }
}
