package com.project.reddital_backend.DTOs.mappers;

import com.project.reddital_backend.DTOs.models.SubRedditDto;
import com.project.reddital_backend.models.SubReddit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;


import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class SubRedditMapperTest {

    // ------------------------------------------------------- properties -------------------------------------------------------

    @InjectMocks
    private SubRedditMapper subRedditMapperUnderTest;

    final String name      = "r/askTal";

    // ------------------------------------------------------- tests -------------------------------------------------------

    @Test
    @DisplayName("test toSubRedditDto")
    public void toSubRedditDto() {
        // Run the test
        SubReddit post = SubReddit.builder()
                .name(name)
                .build();

        final SubRedditDto result = subRedditMapperUnderTest.toSubRedditDto(post);

        assertEquals(name, result.getName());
    }
}
