package com.project.reddital_backend.DTOs.mappers;

import com.project.reddital_backend.DTOs.models.UserDto;
import com.project.reddital_backend.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class UserMapperTest {

    // ------------------------------------------------------- properties -------------------------------------------------------

    private User user;

    // ------------------------------------------------------- preparations -------------------------------------------------------

    @BeforeEach
    public void setUp() {
        user = User.builder()
                .username("Sportalcraft")
                .email("test@test.com")
                .password("123456")
                .build();
    }

    @AfterEach
    public void tearDown() {
        user = null;
    }

    // ------------------------------------------------------- tests -------------------------------------------------------

    @Test
    @DisplayName("test toUserDto")
    public void toUserDto() {
        // Run the test
        final UserDto result = UserMapper.toUserDto(user);

        // Verify the results
        assertEquals(result.getId(), user.getId());
        assertEquals(result.getUsername(), user.getUsername());
        assertEquals(result.getEmail(), user.getEmail());
        assertEquals(result.getPassword(), user.getPassword());
    }

    @Test
    @DisplayName("test toUserDto with null parameter")
    public void toUserDto_null() {
        assertNull(UserMapper.toUserDto(null));
    }
}
