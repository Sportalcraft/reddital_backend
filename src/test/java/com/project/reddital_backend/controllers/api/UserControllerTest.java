package com.project.reddital_backend.controllers.api;

import com.project.reddital_backend.DTOs.models.UserDto;
import com.project.reddital_backend.controllers.requests.Request;
import com.project.reddital_backend.controllers.requests.UserSignupRequest;
import com.project.reddital_backend.services.UserService;
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
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    // ----------------------------------------------------- fields -----------------------------------------------------

    @MockBean
    private UserService mockUserService;

    @Autowired
    private MockMvc mockMvc;



    private UserDto user;

    private final String USER_PATH = "/user";

    private ObjectMapper objectMapper;


    // ------------------------------------------------------- preparations -------------------------------------------------------

    @BeforeEach
    public void setUp() {
        openMocks(this);

        user = UserDto.builder()
                .username("Sportalcraft")
                .email("test@test.com")
                .password("123456")
                .build();

        objectMapper = new ObjectMapper();
    }

    @AfterEach
    public void tearDown() {
        mockUserService = null;
        user = null;
        //objectMapper = null;
    }

    // ----------------------------------------------------- tests -----------------------------------------------------


    @Test
    @DisplayName("test signup with good parameters")
    public void signup_good() throws Exception {
        Mockito.when(mockUserService.signup(any()))
                .thenReturn(null);

        Request request = UserSignupRequest.builder()
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .password(user.getPassword()).build();


        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post(USER_PATH + "/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        );

        result.andDo(print());
    }



    @Test
    public void hello() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                       .get("/helloWorld")
                       .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(content().string("\"Username already taken - please try with different username\""));
    }


    @Test
    public void mock() throws Exception {
        assertNull(null);
    }

}
