package com.project.reddital_backend.DTOs.mappers;

import com.project.reddital_backend.DTOs.models.PostDto;
import com.project.reddital_backend.models.Post;
import com.project.reddital_backend.models.SubReddit;
import com.project.reddital_backend.models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PostMapperTest {

    // ------------------------------------------------------- properties -------------------------------------------------------

    final String title      = "i'm new here!";
    final String content    = "hello! just registered!";
    final Date time         = new Date();

    final User user = User.builder().username("a").password("123456").email("a@a.a").build();
    final SubReddit sub = SubReddit.builder().name("r/askTal").build();

    // ------------------------------------------------------- tests -------------------------------------------------------

    @Test
    @DisplayName("test toPostDto")
    public void toPostDto() {
        // Run the test
        Post post = Post.builder()
                .title(title)
                .content(content)
                .time(time)
                .user(user)
                .subreddit(sub)
                .build();

        final PostDto result = PostMapper.toPostDto(post);

        assertEquals(title, result.getTitle());
        assertEquals(content, result.getContent());
        assertEquals(time, result.getTime());
        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(sub.getName(), result.getSubredditName());
    }
}
