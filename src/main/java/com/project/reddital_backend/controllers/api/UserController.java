package com.project.reddital_backend.controllers.api;

import com.project.reddital_backend.DTOs.models.UserDto;
import com.project.reddital_backend.controllers.requests.UserSignupRequest;
import com.project.reddital_backend.controllers.responses.Response;
import com.project.reddital_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public Response signup(@RequestBody @Valid UserSignupRequest userSignupRequest) {
        return Response.ok().setPayload(registerUser(userSignupRequest));
    }

    private UserDto registerUser(UserSignupRequest userSignupRequest) {
        UserDto userDto = new UserDto()
                //.setId(userSignupRequest.getId())
                .setUsername(userSignupRequest.getUsername())
                .setEmail(userSignupRequest.getEmail())
                .setPassword(userSignupRequest.getPassword());

        return userService.signup(userDto);
    }

}
