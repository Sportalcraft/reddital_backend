package com.project.reddital_backend.DTOs.mappers;

import com.project.reddital_backend.DTOs.models.PostDto;
import com.project.reddital_backend.DTOs.models.UserDto;
import com.project.reddital_backend.controllers.requests.PostingRequest;
import com.project.reddital_backend.models.Post;
import com.project.reddital_backend.models.SubReddit;
import com.project.reddital_backend.models.User;
import com.project.reddital_backend.services.AuthenticationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class PostMapperTest {

    // ------------------------------------------------------- properties -------------------------------------------------------

    @Mock
    private AuthenticationService mockAuthenticationService;

    @InjectMocks
    private PostMapper postMapper;

    final String title      = "i'm new here!";
    final String content    = "hello! just registered!";
    final Date time         = new Date();

    final User user = User.builder().username("a").password("123456").email("a@a.a").build();
    final UserDto udto = UserDto.builder().username(user.getUsername()).build();
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

        final PostDto result = postMapper.toPostDto(post);

        assertEquals(title, result.getTitle());
        assertEquals(content, result.getContent());
        assertEquals(time.getTime(), result.getTime());
        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(sub.getName(), result.getSubReddit());
    }

    @Test
    @DisplayName("test toPostDto")
    public void toPostDto_request() {

        Mockito.when(mockAuthenticationService.authenticate(anyString()))
                .thenReturn(udto);

        // Run the test
        PostingRequest request = PostingRequest.builder()
                .title(title)
                .content(content)
                .subReddit(sub.getName())
                .authenticationKey("42")
                .build();

        final PostDto result = postMapper.toPostDto(request);

        assertEquals(title, result.getTitle());
        assertEquals(content, result.getContent());
        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(sub.getName(), result.getSubReddit());
    }
}
