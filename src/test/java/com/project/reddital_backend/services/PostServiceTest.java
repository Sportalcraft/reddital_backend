package com.project.reddital_backend.services;

import com.project.reddital_backend.DTOs.mappers.PostMapper;
import com.project.reddital_backend.DTOs.models.PostDto;
import com.project.reddital_backend.controllers.requests.PostingRequest;
import com.project.reddital_backend.exceptions.BadParametersException;
import com.project.reddital_backend.exceptions.UnauthorizedException;
import com.project.reddital_backend.models.Post;
import com.project.reddital_backend.models.SubReddit;
import com.project.reddital_backend.models.User;
import com.project.reddital_backend.repositories.PostRepository;
import com.project.reddital_backend.repositories.SubRedditRepository;
import com.project.reddital_backend.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(SpringExtension.class)
class PostServiceTest {

    // ------------------------------------------------------- properties -------------------------------------------------------

    @MockBean
    private PostRepository mockPostRepository;

    @MockBean
    private UserRepository mockUserRepository;

    @MockBean
    private SubRedditRepository mockSubRedditRepository;

    @MockBean
    private PostMapper postMapper;


    @SpyBean
    private PostService postServiceUnderTest;


    private User user;
    private SubReddit sub;
    private Post post;
    private PostDto postDto;

    // ------------------------------------------------------- preparations -------------------------------------------------------

    @BeforeEach
    public void setUp() {

        user = User.builder().username("tal").id(157).build();
        sub = SubReddit.builder().name("r/askTal").id(39).build();

        post = Post.builder()
                .id(42)
                .title("hello evryone")
                .content("this is YOUR dailly dose of tal")
                .time(new Date())
                .subreddit(sub)
                .user(user)
                .build();

        postDto = PostDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .subReddit(post.getSubreddit().getName())
                .username(post.getUser().getUsername())
                .time(new Date().getTime())
                .build();
    }

    @AfterEach
    public void tearDown() {
        post = null;
    }


    // ------------------------------------------------------- tests -------------------------------------------------------

    @Test
    @DisplayName("test posting a post with good values")
    public void posting_good() {

        Mockito.when(mockPostRepository.save(any()))
                .thenReturn(post);

        Mockito.when(mockUserRepository.findByUsername(anyString()))
                .thenReturn(user);

        Mockito.when(mockSubRedditRepository.findByName(anyString()))
                .thenReturn(sub);

        Mockito.when(postMapper.toPostDto(any(), anyString()))
                .thenReturn(postDto);

        Mockito.when(postMapper.toPostDto(any()))
                .thenReturn(postDto);



        PostDto result = postServiceUnderTest.posting(postDto);

        // Verify the results
        assertEquals(postDto.getTitle(), result.getTitle());
        assertEquals(postDto.getContent(), result.getContent());
        assertEquals(postDto.getSubReddit(), result.getSubReddit());
        assertTrue(result.getId() >= 0);
        assertTrue(Math.abs(new Date().getTime() - result.getTime()) < 1000);
    }

    @Test
    @DisplayName("test posting a post with with a bad user")
    public void posting_badUser() {

        Mockito.when(mockPostRepository.save(any()))
                .thenReturn(post);

        Mockito.when(mockUserRepository.findByUsername(anyString()))
                .thenReturn(null);

        Mockito.when(mockSubRedditRepository.findByName(anyString()))
                .thenReturn(sub);

        Mockito.when(postMapper.toPostDto(any(), anyString()))
                .thenReturn(postDto);


        // should throw an error
        assertThrows(UnauthorizedException.class, () -> postServiceUnderTest.posting(postDto));
    }

    @Test
    @DisplayName("test posting a post with with a bad subreddit")
    public void posting_badsubReddit() {

        Mockito.when(mockPostRepository.save(any()))
                .thenReturn(post);

        Mockito.when(mockUserRepository.findByUsername(anyString()))
                .thenReturn(user);

        Mockito.when(mockSubRedditRepository.findByName(anyString()))
                .thenReturn(null);

        Mockito.when(postMapper.toPostDto(any(), anyString()))
                .thenReturn(postDto);


        // should throw an error
        assertThrows(BadParametersException.class, () -> postServiceUnderTest.posting(postDto));
    }

    @Test
    @DisplayName("test posting a post with with a bad subreddit")
    public void posting_badsubReddit2() {

        Mockito.when(mockPostRepository.save(any()))
                .thenReturn(null);

        Mockito.when(mockUserRepository.findByUsername(anyString()))
                .thenReturn(user);

        Mockito.when(mockSubRedditRepository.findByName(anyString()))
                .thenReturn(sub);

        Mockito.when(postMapper.toPostDto(any(), anyString()))
                .thenReturn(postDto);


        assertNull(postServiceUnderTest.posting(postDto));
    }


}