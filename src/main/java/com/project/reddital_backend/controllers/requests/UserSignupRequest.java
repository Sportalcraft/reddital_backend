package com.project.reddital_backend.controllers.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    public void clean() {
        setUsername(removeWhiteSpace(getUsername()));
        setEmail(removeWhiteSpace(getEmail()));
        setPassword(removeWhiteSpace(getPassword()));
    }

    @Override
    public String validate() {
        String ans = null;
        clean();

        if(nullOrEmpty(getUsername())) {
            ans = "username is missing!";
        } else if (nullOrEmpty(getEmail())) {
            ans = "email is missing!";
        } else if (nullOrEmpty(getPassword())) {
            ans = "password is missing!";
        } else if (getPassword().length() < 6) {
            ans = "password is too short!";
        } else if (isValidEmailAddress(getEmail())) {
            ans = "email is not valid";
        }

        return ans;
    }


    // --------------------------------------- private methods

    public static boolean isValidEmailAddress(String email) {
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            return false;
        }
        return true;
    }
}