package com.project.reddital_backend.controllers.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.project.reddital_backend.DTOs.mappers.PostMapper;
import com.project.reddital_backend.DTOs.models.PostDto;
import com.project.reddital_backend.controllers.requests.PostingRequest;
import com.project.reddital_backend.controllers.requests.Request;
import com.project.reddital_backend.exceptions.UnauthorizedException;
import com.project.reddital_backend.services.PostService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Date;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PostController.class)
public class PostControllerTest {

    // ----------------------------------------------------- fields -----------------------------------------------------

    @MockBean
    private PostMapper mockPostMapper;

    @MockBean
    private PostService mockPostService;

    @Autowired
    private MockMvc mockMvc;


    private ObjectMapper objectMapper;

    private final String POST_PATH  = "";

    final String title = "i'm tal, AMA";
    final String content = "all questions will be answered!";
    final String subReddit = "r/askTal";
    final String authentication = "0";
    final String username = "tal";

    private PostDto pdto;


    // ------------------------------------------------------- preparations -------------------------------------------------------

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        pdto = PostDto.builder()
                .title(title)
                .content(content)
                .subReddit(subReddit)
                .username(username)
                .build();
    }

    @AfterEach
    public void tearDown() {
        objectMapper = null;
        pdto = null;
    }

    // ----------------------------------------------------- tests -----------------------------------------------------


    @Test
    @DisplayName("test posting with good parameters")
    public void posting_good() throws Exception {

        //mock such that the same input will be returned
        Mockito.when(mockPostService.posting(any())).thenAnswer((Answer<PostDto>) invocation -> {
            Object[] args = invocation.getArguments();
            PostDto post =  (PostDto) args[0];
            return post.setTime(new Date().getTime());
        });

        Mockito.when(mockPostMapper.toPostDto(any(), anyString()))
                .thenReturn(pdto);

        Request request = PostingRequest.builder()
                .title(title)
                .content(content)
                .authenticationKey(authentication)
                .build();

        ResultActions result = post(POST_PATH + "/posting", requestAsString(request), subReddit);
        String body = result.andReturn().getResponse().getContentAsString();

        result.andExpect(status().isCreated());

        checkProperty(title, "$.title", body);
        checkProperty(content, "$.content", body);
        checkProperty(subReddit, "$.subReddit", body);

        long timestamp = JsonPath.read(body, "$.time");

        assertTrue(Math.abs(timestamp - new Date().getTime()) < 1000);
    }


    @Test
    @DisplayName("test posting with user that does not exist")
    public void posting_noKey() throws Exception {
        Mockito.when(mockPostService.posting(any())).thenThrow(UnauthorizedException.class);

        Request request = PostingRequest.builder()
                .title(title)
                .content(content)
                .build();

        ResultActions result = post(POST_PATH + "/posting", requestAsString(request), subReddit);
        result.andExpect(status().isUnauthorized());
    }

    // ----------------------------------------------------- private methods -----------------------------------------------------

    private void checkProperty(String expected, String jsonPath, String body){
        assertEquals(expected, "" + JsonPath.read(body, jsonPath));
    }

    private ResultActions post(String uri, String content, String subreddital) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .param("subreddital", subreddital)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        );
    }

    private String requestAsString(Request request) throws JsonProcessingException {
        return objectMapper.writeValueAsString(request);
    }


}
