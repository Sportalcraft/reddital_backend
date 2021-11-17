package com.project.reddital_backend.services;

import com.project.reddital_backend.DTOs.models.UserDto;
import com.project.reddital_backend.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * the goal of this class is authenticating the authentication key that was received by the user
 */
@Component
public class AuthenticationService {

    @Autowired
    private UserService userService;

    /**
     * get an authentication key, return the user it belongs to.
     * @param authenticationKey the authentication key
     * @return  the user it belongs to, null if key is not valid or expired
     */
    public UserDto authenticate(String authenticationKey) {
        return userService.findUserById(key2id(authenticationKey));
    }

    // -------------------------------- private methods --------------------------------

    /**
     * transform an authentication key to user id
     * @param authenticationKey the authentication key
     * @return the user id it belongs to, null if key is not valid or expired
     */
    private Long key2id(String authenticationKey) {
        return Long.parseLong(authenticationKey);
    }

}
