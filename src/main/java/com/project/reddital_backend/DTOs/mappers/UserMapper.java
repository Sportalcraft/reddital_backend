package com.project.reddital_backend.DTOs.mappers;

import com.project.reddital_backend.DTOs.models.UserDto;
import com.project.reddital_backend.controllers.requests.SignupRequest;
import com.project.reddital_backend.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    /**
     * transform a user object into a user dto
     * @param user the user object
     * @return the mapped user dto
     */
    public UserDto toUserDto(User user) {

        if(user == null)
            return null;

        return new UserDto()
                .setId(user.getId())
                .setEmail(user.getEmail())
                .setUsername(user.getUsername())
                .setPassword(user.getPassword());
    }

    /**
     * transform a signup request into a user dto
     * @param signupRequest the signup request
     * @return the mapped user dto
     */
    public UserDto toUserDto(SignupRequest signupRequest){
        if(signupRequest == null)
            return null;

        return new UserDto()
                .setUsername(signupRequest.getUsername())
                .setEmail(signupRequest.getEmail())
                .setPassword(signupRequest.getPassword());
    }

}