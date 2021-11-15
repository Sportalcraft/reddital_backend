package com.project.reddital_backend.DTOs.mappers;

import com.project.reddital_backend.DTOs.models.PostDto;
import com.project.reddital_backend.DTOs.models.SubRedditDto;
import com.project.reddital_backend.models.Post;
import com.project.reddital_backend.models.SubReddit;
import com.project.reddital_backend.models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubRedditMapperTest {
}



public class PostMapperTest {

    // ------------------------------------------------------- properties -------------------------------------------------------

    final String name      = "r/askTal";

    // ------------------------------------------------------- tests -------------------------------------------------------

    @Test
    @DisplayName("test toSubRedditDto")
    public void toSubRedditDto() {
        // Run the test
        SubReddit post = SubReddit.builder()
                .name(name)
                .build();

        final SubRedditDto result = SubRedditMapper.toSubRedditDto(post);

        assertEquals(name, result.getName());
    }
}
