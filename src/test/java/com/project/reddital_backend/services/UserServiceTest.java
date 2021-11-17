package com.project.reddital_backend.services;

import com.project.reddital_backend.DTOs.mappers.UserMapper;
import com.project.reddital_backend.DTOs.models.UserDto;
import com.project.reddital_backend.exceptions.DuplicateEntityException;
import com.project.reddital_backend.exceptions.EntityNotFoundException;
import com.project.reddital_backend.exceptions.UnauthorizedException;
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

import static org.junit.jupiter.api.Assertions.*;
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

    @MockBean
    private UserMapper userMapper;

    @SpyBean
    private UserService userServiceUnderTest;



    private User user;

    private UserDto udto;

    // ------------------------------------------------------- preparations -------------------------------------------------------

    @BeforeEach
    public void setUp() {
        //openMocks(this);

        user = User.builder()
                .username("Sportalcraft")
                .email("test@test.com")
                .password("123456")
                .build();

        udto = UserDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getEmail())
                .build();
    }

    @AfterEach
    public void tearDown() {
        user = null;
        udto = null;
        userServiceUnderTest = null;
    }





    // ------------------------------------------------------- tests -------------------------------------------------------

    @Test
    @DisplayName("test signup with existing username")
    public void signup_userExist() {
        Mockito.when(mockUserRepository.findByUsername(anyString()))
                .thenReturn(user);

        Mockito.when(userMapper.toUserDto((User) any()))
                .thenReturn(udto);

        assertThrows(DuplicateEntityException.class, () -> {
            userServiceUnderTest.signup(userMapper.toUserDto(user));
        });
    }

    @Test
    @DisplayName("test signup with a new username")
    public void signup_newUser() {
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

        Mockito.when(userMapper.toUserDto((User) any()))
                .thenReturn(udto);


        // Run the test
        final UserDto result = userServiceUnderTest.signup(udto);

        // Verify the results
        assertEquals(user.getUsername(), result.getUsername());
    }




    @Test
    @DisplayName("test findUserById with an existing user")
    public void testFindUserByID_exist() {
        // Setup
        final long id = user.getId();

        Mockito.when(mockUserRepository.findById(anyLong()))
                .thenReturn(user);

        Mockito.when(userMapper.toUserDto((User) any()))
                .thenReturn(udto);

        // Run the test
        final UserDto result = userServiceUnderTest.findUserById(id);

        // Verify the results
        assertEquals(id, result.getId());
    }

    @Test
    @DisplayName("test findUserById with a non existing user")
    public void testFindUserByID_nonExist() {
        // edit the mocking
        Mockito.when(mockUserRepository.findById(anyLong()))
                .thenReturn(null);

        Mockito.when(userMapper.toUserDto((User) any()))
                .thenReturn(udto);

        assertThrows(EntityNotFoundException.class, () -> {
            userServiceUnderTest.findUserById(user.getId());
        });
    }





    @Test
    @DisplayName("test findUserByUsername with existing user")
    public void testFindUserByUsername_exist() {

        Mockito.when(mockUserRepository.findByUsername(anyString()))
                .thenReturn(user);

        Mockito.when(userMapper.toUserDto((User) any()))
                .thenReturn(udto);

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

            Mockito.when(userMapper.toUserDto((User) any()))
                    .thenReturn(udto);

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

        Mockito.when(userMapper.toUserDto((User) any()))
                .thenReturn(udto);

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

            Mockito.when(userMapper.toUserDto((User) any()))
                    .thenReturn(udto);

            final UserDto dto = userServiceUnderTest.findUserByUsername(username)
                    .setEmail(newEmail);

            userServiceUnderTest.updateProfile(dto);
        });
    }



    @Test
    @DisplayName("test changePassword with existing user")
    public void changePassword_exist() {
        final String newPass = "lrno4!dhvoASDednbcokeA$%%!@pl;km"; // The force is strong with this one!

        Mockito.when(mockUserRepository.findByUsername(anyString()))
                .thenReturn(user);

        Mockito.when(mockUserRepository.save(any()))
                .thenReturn(user.setPassword(newPass));

        Mockito.when(userMapper.toUserDto((User) any()))
                .thenReturn(udto.setPassword(newPass));

        // return the password that was received (encrypting is non-important for those unit testing)
        Mockito.when(bCryptPasswordEncoder.encode(anyString())).thenAnswer((Answer<String>) invocation -> {
            Object[] args = invocation.getArguments();
            return (String) args[0];
        });


        final UserDto result = userServiceUnderTest.changePassword(udto, newPass);

        // Verify the results
        assertEquals(newPass, result.getPassword());
        assertEquals(newPass, userServiceUnderTest.findUserByUsername(user.getUsername()).getPassword());
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

            Mockito.when(userMapper.toUserDto((User) any()))
                    .thenReturn(udto);


            userServiceUnderTest.changePassword(dto, newPass);
        });
    }

    @Test
    @DisplayName("test login with good info")
    public void login_good() {
        Mockito.when(mockUserRepository.findByUsername(anyString()))
                .thenReturn(user);

        Mockito.when(userMapper.toUserDto((User) any()))
                .thenReturn(udto);

        // return if the password matches
        Mockito.when(bCryptPasswordEncoder.matches(anyString(),anyString())).thenAnswer((Answer<Boolean>) invocation -> {
            Object[] args = invocation.getArguments();
            return args[0].equals(args[1]);
        });


        //get result
        final UserDto result = userServiceUnderTest.login(user.getUsername(), user.getPassword());

        // Verify the results
        assertNotNull(result);
        assertEquals(user.getUsername(), result.getUsername());
    }

    @Test
    @DisplayName("test login with a username that does not exist")
    public void login_nonExistUser() {
        assertThrows(EntityNotFoundException.class, ()->{
            Mockito.when(mockUserRepository.findByUsername(anyString()))
                    .thenReturn(user);

            Mockito.when(userMapper.toUserDto((User) any()))
                    .thenReturn(udto);

            // return if the password matches
            Mockito.when(bCryptPasswordEncoder.matches(anyString(),anyString())).thenAnswer((Answer<Boolean>) invocation -> {
                Object[] args = invocation.getArguments();
                return args[0].equals(args[1]);
            });


            //get result
            userServiceUnderTest.login(null, user.getPassword());
        });
    }

    @Test
    @DisplayName("test login with the wrong password")
    public void login_wrongPassword() {
        assertThrows(UnauthorizedException.class, ()->{
            Mockito.when(mockUserRepository.findByUsername(anyString()))
                    .thenReturn(user);

            Mockito.when(userMapper.toUserDto((User) any()))
                    .thenReturn(udto);

            // return if the password matches
            Mockito.when(bCryptPasswordEncoder.matches(anyString(),anyString())).thenAnswer((Answer<Boolean>) invocation -> {
                Object[] args = invocation.getArguments();
                return args[0].equals(args[1]);
            });


            //get result
            userServiceUnderTest.login(user.getUsername(), user.getPassword() + "_banana?");
        });
    }


}
