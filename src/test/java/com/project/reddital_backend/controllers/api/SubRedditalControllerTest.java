package com.project.reddital_backend.controllers.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.project.reddital_backend.DTOs.mappers.SubRedditMapper;
import com.project.reddital_backend.DTOs.models.SubRedditDto;
import com.project.reddital_backend.controllers.requests.AddSubRequest;
import com.project.reddital_backend.controllers.requests.Request;
import com.project.reddital_backend.exceptions.DuplicateEntityException;
import com.project.reddital_backend.services.SubRedditService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SubRedditalController.class)
class SubRedditalControllerTest {

    // ----------------------------------------------------- fields -----------------------------------------------------

    @MockBean
    private SubRedditMapper mockSubMapper;

    @MockBean
    private SubRedditService mockSubService;

    @Autowired
    private MockMvc mockMvc;




    private ObjectMapper objectMapper;

    private final String SUB_PATH  = "/subredditals";

    final String name = "askTal";

    private SubRedditDto sdto;

    // ------------------------------------------------------- preparations -------------------------------------------------------

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        sdto = SubRedditDto.builder()
                .name(name)
                .build();
    }

    @AfterEach
    public void tearDown() {
        objectMapper = null;
        sdto = null;
    }

    // ----------------------------------------------------- tests -----------------------------------------------------


    @Test
    @DisplayName("test create sub")
    public void create_good() throws Exception {
        Mockito.when(mockSubService.create(any()))
                .thenReturn(sdto);

        Request request = AddSubRequest.builder()
                .name(name)
                .build();

        ResultActions result = post(SUB_PATH + "/create", requestAsString(request));
        String body = result.andReturn().getResponse().getContentAsString();

        result.andExpect(status().isCreated());

        checkProperty(name, "$.name", body);
    }

    @Test
    @DisplayName("test create sub that exist")
    public void create_exist() throws Exception {
        Mockito.when(mockSubService.create(any()))
                .thenThrow(DuplicateEntityException.class);

        Request request = AddSubRequest.builder()
                .name(name)
                .build();

        ResultActions result = post(SUB_PATH + "/create", requestAsString(request));

        result.andExpect(status().isUnprocessableEntity());
    }

    // ----------------------------------------------------- private methods -----------------------------------------------------

    private void checkProperty(String expected, String jsonPath, String body){
        assertEquals(expected, "" + JsonPath.read(body, jsonPath));
    }

    private ResultActions post(String uri, String content) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        );
    }

    private String requestAsString(Request request) throws JsonProcessingException {
        return objectMapper.writeValueAsString(request);
    }
}