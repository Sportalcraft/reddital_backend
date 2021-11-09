package com.project.reddital_backend.controllers.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.reddital_backend.exceptions.BadParametersException;
import lombok.*;
import lombok.experimental.Accessors;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSignupRequest extends Request {

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private String username;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private String email;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private String password;

    @Override
    public void validate() {
        String ans = null;

        if(nullOrEmpty(getUsername())) {
            ans = "username is missing!";
        } else if (nullOrEmpty(getEmail())) {
            ans = "email is missing!";
        } else if (nullOrEmpty(getPassword())) {
            ans = "password is missing!";
        }

       else if (haveWhiteSpaces(getUsername())){
            ans = "username cannot contains white spaces!";
        }else if (haveWhiteSpaces(getEmail())){
            ans = "email cannot contains white spaces!";
        }else if (haveWhiteSpaces(getPassword())){
            ans = "password cannot contains white spaces!";
        }

        else if (getPassword().length() < 6) {
            ans = "password is too short!";
        } else if (!isValidEmailAddress(getEmail())) {
            ans = "email is not valid";
        }

        if(ans != null){
            throw new BadParametersException(ans);
        }
    }


    // --------------------------------------- private methods ---------------------------------------

    private boolean isValidEmailAddress(String email) {
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            return false;
        }
        return true;
    }
}