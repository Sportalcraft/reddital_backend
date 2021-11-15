package com.project.reddital_backend.controllers.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.project.reddital_backend.DTOs.models.PostDto;
import com.project.reddital_backend.DTOs.models.UserDto;
import com.project.reddital_backend.controllers.requests.PostingRequest;
import com.project.reddital_backend.controllers.requests.Request;
import com.project.reddital_backend.controllers.requests.SignupRequest;
import com.project.reddital_backend.services.PostService;
import com.project.reddital_backend.services.UserService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Date;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class PostControllerTest {

    // ----------------------------------------------------- fields -----------------------------------------------------

    @MockBean
    private PostService mockPostService;

    @Autowired
    private MockMvc mockMvc;


    private ObjectMapper objectMapper;

    private final String USER_PATH  = "/post";

    final String title = "i'm tal, AMA";
    final String content = "all questions will be answered!";
    final String subReddit = "r/askTal";
    final String authentication = "0";


    // ------------------------------------------------------- preparations -------------------------------------------------------

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @AfterEach
    public void tearDown() {
        objectMapper = null;
    }

    // ----------------------------------------------------- tests -----------------------------------------------------

    @Test
    @DisplayName("test posting with good parameters")
    public void posting_good() throws Exception {

        //mock such that the same input will be returned
        Mockito.when(mockPostService.posting(any())).thenAnswer((Answer<PostDto>) invocation -> {
            Object[] args = invocation.getArguments();
            return (PostDto) args[0];
        });

        Request request = PostingRequest.builder()
                .title(title)
                .content(content)
                .subReddit(subReddit)
                .authenticationKey(authentication)
                .build();

        ResultActions result = post(USER_PATH + "/posting", requestAsString(request));
        String body = result.andReturn().getResponse().getContentAsString();

        result.andExpect(status().isCreated());

        checkProperty(title, "$.title", body);
        checkProperty(content, "$.content", body);
        checkProperty(subReddit, "$.subReddit", body);

        assertTrue(Math.abs(Long.parseLong(JsonPath.read(body, "$.time")) - new Date().getTime()) < 1000);
    }

    // ----------------------------------------------------- private methods -----------------------------------------------------

    private void checkProperty(String expected, String jsonPath, String body){
        assertEquals(expected, "" + JsonPath.read(body, jsonPath));
    }

    private ResultActions post(String uri, String content) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        );
    }

    private String requestAsString(Request request) throws JsonProcessingException {
        return objectMapper.writeValueAsString(request);
    }


}
