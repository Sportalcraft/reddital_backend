package com.project.reddital_backend.services;

import com.project.reddital_backend.DTOs.mappers.UserMapper;
import com.project.reddital_backend.DTOs.models.UserDto;
import com.project.reddital_backend.exceptions.DuplicateEntityException;
import com.project.reddital_backend.exceptions.EntityNotFoundException;
import com.project.reddital_backend.models.User;
import com.project.reddital_backend.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    // ------------------------------------------------------- properties -------------------------------------------------------

    @MockBean
    private UserRepository mockUserRepository;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //@Autowired
    //@InjectMocks
    @SpyBean
    private UserService userServiceUnderTest;



    private User user;

    // ------------------------------------------------------- preparations -------------------------------------------------------

    @BeforeEach
    public void setUp() {
        //openMocks(this);

        user = User.builder()
                .username("Sportalcraft")
                .email("test@test.com")
                .password("123456")
                .build();
    }

    @AfterEach
    public void tearDown() {
        user = null;
        userServiceUnderTest = null;
    }





    // ------------------------------------------------------- tests -------------------------------------------------------

    @Test
    @DisplayName("test signup with existing username")
    public void signup_userExist() {
        assertThrows(DuplicateEntityException.class, () -> {
            Mockito.when(mockUserRepository.findByUsername(anyString()))
                    .thenReturn(user);

            userServiceUnderTest.signup(UserMapper.toUserDto(user));
        });
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

        Mockito.when(mockUserRepository.findById(anyLong()))
                .thenReturn(user);

        // Run the test
        final UserDto result = userServiceUnderTest.findUserById(id);

        // Verify the results
        assertEquals(id, result.getId());
    }

    @Test
    @DisplayName("test findUserById with a non existing user")
    public void testFindUserByID_nonExist() {
        assertThrows(EntityNotFoundException.class, () -> {
            // Setup
            final long id = user.getId();

            // edit the mocking
            Mockito.when(mockUserRepository.findById(anyLong()))
                    .thenReturn(null);

            // Run the test
            userServiceUnderTest.findUserById(id);
        });
    }





    @Test
    @DisplayName("test findUserByUsername with existing user")
    public void testFindUserByUsername_exist() {

        Mockito.when(mockUserRepository.findByUsername(anyString()))
                .thenReturn(user);

        // Setup
        final String username = user.getUsername();

        // Run the test
        final UserDto result = userServiceUnderTest.findUserByUsername(username);

        // Verify the results
        assertEquals(username, result.getUsername());
    }

    @Test
    @DisplayName("test findUserByUsername with a non existing user")
    public void testFindUserByUsername_nonExist() {
        assertThrows(EntityNotFoundException.class, () -> {
            // Setup
            final String username = user.getUsername();

            // edit the mocking
            Mockito.when(mockUserRepository.findByUsername(anyString()))
                    .thenReturn(null);

            // Run the test
            userServiceUnderTest.findUserByUsername(username);
        });
    }





    @Test
    @DisplayName("test updateProfile with existing user")
    public void testUpdateProfile_exist() {
        final String username = user.getUsername();
        final String newEmail = "newEmail@yosi.com";

        Mockito.when(mockUserRepository.findByUsername(anyString()))
                .thenReturn(user);

        Mockito.when(mockUserRepository.save(any()))
                .thenReturn(user);

        final UserDto dto = userServiceUnderTest.findUserByUsername(username)
                .setEmail(newEmail);

        // update the profile
        final UserDto result = userServiceUnderTest.updateProfile(dto);

        // Verify the results
        assertEquals(newEmail, result.getEmail());
        assertEquals(newEmail, userServiceUnderTest.findUserByUsername(username).getEmail());
    }

    @Test
    @DisplayName("test updateProfile with a non-existing user")
    public void testUpdateProfile_nonExist() {
        assertThrows(EntityNotFoundException.class, () -> {
            final String username = user.getUsername();
            final String newEmail = "newEmail@yosi.com";

            // edit the mocking
            Mockito.when(mockUserRepository.findByUsername(anyString()))
                    .thenReturn(null);

            final UserDto dto = userServiceUnderTest.findUserByUsername(username)
                    .setEmail(newEmail);

            userServiceUnderTest.updateProfile(dto);
        });
    }



    @Test
    @DisplayName("test changePassword with existing user")
    public void changePassword_exist() {
        Mockito.when(mockUserRepository.findByUsername(anyString()))
                .thenReturn(user);

        Mockito.when(mockUserRepository.save(any()))
                .thenReturn(user);

        // return the password that was received (encrypting is non-important for those unit testing)
        Mockito.when(bCryptPasswordEncoder.encode(anyString())).thenAnswer((Answer<String>) invocation -> {
            Object[] args = invocation.getArguments();
            return (String) args[0];
        });


        final String username = user.getUsername();
        final String newPass = "lrno4!dhvoASDednbcokeA$%%!@pl;km"; // The force is strong with this one!

        final UserDto dto = userServiceUnderTest.findUserByUsername(username);

        final UserDto result = userServiceUnderTest.changePassword(dto, newPass);

        // Verify the results
        assertEquals(newPass, result.getPassword());
        assertEquals(newPass, userServiceUnderTest.findUserByUsername(username).getPassword());
    }

    @Test
    @DisplayName("test changePassword with a non-existing user")
    public void changePassword_nonExist() {
        assertThrows(EntityNotFoundException.class, () -> {
            final String username = user.getUsername();
            final String newPass = "lrno4!dhvoASDednbcokeA$%%!@pl;km"; // The force is strong with this one!

            final UserDto dto = userServiceUnderTest.findUserByUsername(username);

            // edit the mocking
            Mockito.when(mockUserRepository.findByUsername(anyString()))
                    .thenReturn(null);


            userServiceUnderTest.changePassword(dto, newPass);
        });
    }

}
