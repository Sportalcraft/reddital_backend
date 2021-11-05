package com.project.reddital_backend.services;

import com.project.reddital_backend.DTOs.models.UserDto;
import com.project.reddital_backend.models.User;
import com.project.reddital_backend.repositories.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.MockitoAnnotations.openMocks;

public class UserServiceTest {

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private BCryptPasswordEncoder mockBCryptPasswordEncoder;

    @Mock
    private ModelMapper modelMapper;

    private UserService userServiceUnderTest;
    private User user;

    @Before
    public void setUp() {
        openMocks(this);
        userServiceUnderTest = new UserService(mockUserRepository, mockBCryptPasswordEncoder,modelMapper);

        user = User.builder()
                .id(1)
                .username("Sportalcraft")
                .email("test@test.com")
                .build();

        Mockito.when(mockUserRepository.save(any()))
                .thenReturn(user);

        Mockito.when(mockUserRepository.findByUsername(anyString()))
                .thenReturn(user);

        Mockito.when(mockUserRepository.findById(anyLong()))
                .thenReturn(user);
    }

    @After
    public void tearDown() {
        user = null;
        userServiceUnderTest = null;
    }

    @Test
    @DisplayName("test findUserById")
    public void testFindUserByID() {
        // Setup
        final long id = user.getId();

        // Run the test
        final UserDto result = userServiceUnderTest.findUserById(id);

        // Verify the results
        assertEquals(id, result.getId());
    }

    @Test
    @DisplayName("test findUserByUsername")
    public void testFindUserByUsername() {
        // Setup
        final String username = user.getUsername();

        // Run the test
        final UserDto result = userServiceUnderTest.findUserByUsername(username);

        // Verify the results
        assertEquals(username, result.getUsername());
    }

    @Test
    public void testSaveUser() {
        // Setup
        final String username = user.getUsername();

        // Run the test
        UserDto result = userServiceUnderTest.saveUser(User.builder().build());

        // Verify the results
        assertEquals(username, result.getUsername());
    }
}
