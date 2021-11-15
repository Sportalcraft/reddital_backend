package com.project.reddital_backend.DTOs.mappers;

import com.project.reddital_backend.DTOs.models.SubRedditDto;
import com.project.reddital_backend.models.SubReddit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class SubRedditMapperTest {

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
