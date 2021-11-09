package com.project.reddital_backend.controllers.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.reddital_backend.exceptions.BadParametersException;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginRequest extends Request {

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private String username;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private String password;

    @Override
    public void validate() {
        String ans = null;

        if(nullOrEmpty(getUsername())) {
            ans = "username is missing!";
        } else if (nullOrEmpty(getPassword())) {
            ans = "password is missing!";
        }

        else if (haveWhiteSpaces(getUsername())){
            ans = "username cannot contains white spaces!";
        }else if (haveWhiteSpaces(getPassword())){
            ans = "password cannot contains white spaces!";
        }

        if(ans != null){
            throw new BadParametersException(ans);
        }
    }
}