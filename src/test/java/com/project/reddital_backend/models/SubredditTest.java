package com.project.reddital_backend.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class SubredditTest {
    // ------------------------------------------------------- properties -------------------------------------------------------

    final String name      = "r/askTal";


    // ------------------------------------------------------- tests -------------------------------------------------------

    @Test
    @DisplayName("test creating new user")
    public void buildUser() {

        Subreddit sub = Subreddit.builder().name(name).build();

        assertEquals(name, sub.getName());
    }
}
