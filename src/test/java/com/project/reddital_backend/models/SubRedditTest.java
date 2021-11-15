package com.project.reddital_backend.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
