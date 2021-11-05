package com.project.reddital_backend.services;

import com.project.reddital_backend.DTOs.mappers.UserMapper;
import com.project.reddital_backend.DTOs.models.UserDto;
import com.project.reddital_backend.exceptions.DuplicateEntityException;
import com.project.reddital_backend.exceptions.EntityNotFoundException;
import com.project.reddital_backend.models.User;
import com.project.reddital_backend.repositories.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.MockitoAnnotations.openMocks;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    // ------------------------------------------------------- properties -------------------------------------------------------

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserService userServiceUnderTest;



    private User user;






    // ------------------------------------------------------- preparations -------------------------------------------------------

    @Before
    public void setUp() {
        openMocks(this);

        user = User.builder()
                .username("Sportalcraft")
                .email("test@test.com")
                .password("123456")
                .build();

        // return the password that was received (encrypting is non-important for those unit testing)
        Mockito.when(bCryptPasswordEncoder.encode(anyString())).thenAnswer((Answer<String>) invocation -> {
            Object[] args = invocation.getArguments();
            return (String) args[0];
        });

        // return the user that was received
        Mockito.when(mockUserRepository.save(any())).thenAnswer((Answer<User>) invocation -> {
            Object[] args = invocation.getArguments();
            return (User) args[0];
        });

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





    // ------------------------------------------------------- tests -------------------------------------------------------

    @Test(expected = DuplicateEntityException.class)
    @DisplayName("test signup with existing username")
    public void signup_userExist() {
        userServiceUnderTest.signup(UserMapper.toUserDto(user));
    }

    @Test
    @DisplayName("test signup with a new username")
    public void signup_newUser() {
        // Setup
        final String username = "yosiTheKing";
        final UserDto user2 = UserDto.builder()
                .username(username)
                .email("test@test.com")
                .password("123456")
                .build();

        //change to mocking to fut the signup method
        Mockito.when(mockUserRepository.findByUsername(anyString()))
                .thenReturn(null);

        // Run the test
        final UserDto result = userServiceUnderTest.signup(user2);

        // Verify the results
        assertEquals(username, result.getUsername());
    }




    @Test
    @DisplayName("test findUserById with an existing user")
    public void testFindUserByID_exist() {
        // Setup
        final long id = user.getId();

        // Run the test
        final UserDto result = userServiceUnderTest.findUserById(id);

        // Verify the results
        assertEquals(id, result.getId());
    }

    @Test(expected = EntityNotFoundException.class)
    @DisplayName("test findUserById with a non existing user")
    public void testFindUserByID_nonExist() {
        // Setup
        final long id = user.getId();

        // edit the mocking
        Mockito.when(mockUserRepository.findById(anyLong()))
                .thenReturn(null);

        // Run the test
        userServiceUnderTest.findUserById(id);
    }





    @Test
    @DisplayName("test findUserByUsername with existing user")
    public void testFindUserByUsername_exist() {
        // Setup
        final String username = user.getUsername();

        // Run the test
        final UserDto result = userServiceUnderTest.findUserByUsername(username);

        // Verify the results
        assertEquals(username, result.getUsername());
    }

    @Test(expected = EntityNotFoundException.class)
    @DisplayName("test findUserByUsername with a non existing user")
    public void testFindUserByUsername_nonExist() {
        // Setup
        final String username = user.getUsername();

        // edit the mocking
        Mockito.when(mockUserRepository.findByUsername(anyString()))
                .thenReturn(null);

        // Run the test
        userServiceUnderTest.findUserByUsername(username);
    }





    @Test
    @DisplayName("test updateProfile with existing user")
    public void testUpdateProfile_exist() {
        final String username = user.getUsername();
        final String newEmail = "newEmail@yosi.com";

        final UserDto dto = userServiceUnderTest.findUserByUsername(username)
                .setEmail(newEmail);

        // update the profile
        final UserDto result = userServiceUnderTest.updateProfile(dto);

        // Verify the results
        assertEquals(newEmail, result.getEmail());
        assertEquals(newEmail, userServiceUnderTest.findUserByUsername(username).getEmail());
    }

    @Test(expected = EntityNotFoundException.class)
    @DisplayName("test updateProfile with a non-existing user")
    public void testUpdateProfile_nonExist() {
        final String username = user.getUsername();
        final String newEmail = "newEmail@yosi.com";

        final UserDto dto = userServiceUnderTest.findUserByUsername(username)
                .setEmail(newEmail);

        // edit the mocking
        Mockito.when(mockUserRepository.findByUsername(anyString()))
                .thenReturn(null);

        userServiceUnderTest.updateProfile(dto);
    }



    @Test
    @DisplayName("test changePassword with existing user")
    public void changePassword_exist() {
        final String username = user.getUsername();
        final String newPass = "lrno4!dhvoASDednbcokeA$%%!@pl;km"; // The force is strong with this one!

        final UserDto dto = userServiceUnderTest.findUserByUsername(username);

        final UserDto result = userServiceUnderTest.changePassword(dto, newPass);

        // Verify the results
        assertEquals(newPass, result.getPassword());
        assertEquals(newPass, userServiceUnderTest.findUserByUsername(username).getPassword());
    }

    @Test(expected = EntityNotFoundException.class)
    @DisplayName("test changePassword with a non-existing user")
    public void changePassword_nonExist() {
        final String username = user.getUsername();
        final String newPass = "lrno4!dhvoASDednbcokeA$%%!@pl;km"; // The force is strong with this one!

        final UserDto dto = userServiceUnderTest.findUserByUsername(username);

        // edit the mocking
        Mockito.when(mockUserRepository.findByUsername(anyString()))
                .thenReturn(null);
        userServiceUnderTest.changePassword(dto, newPass);
    }

}
