package com.project.reddital_backend.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PostTest {

    // ------------------------------------------------------- properties -------------------------------------------------------

    final String title      = "i'm new here!";
    final String content    = "hello! just registered!";
    final Date time         = new Date();

    final User user = User.builder().username("a").password("123456").email("a@a.a").build();
    final SubReddit sub = SubReddit.builder().name("r/askTal").build();

    // ------------------------------------------------------- tests -------------------------------------------------------

    @Test
    @DisplayName("test creating new user")
    public void buildUser() {

        Post post = Post.builder()
                .title(title)
                .content(content)
                .time(time)
                .user(user)
                .subreddit(sub)
                .build();

        assertEquals(title, post.getTitle());
        assertEquals(content, post.getContent());
        assertEquals(time, post.getTime());
        assertEquals(user, post.getUser());
        assertEquals(sub, post.getSubreddit());
    }
}
