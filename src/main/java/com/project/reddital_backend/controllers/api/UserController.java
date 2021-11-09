package com.project.reddital_backend.controllers.api;

import com.project.reddital_backend.DTOs.models.UserDto;
import com.project.reddital_backend.controllers.requests.UserSignupRequest;
import com.project.reddital_backend.controllers.responses.Response;
import com.project.reddital_backend.exceptions.DuplicateEntityException;
import com.project.reddital_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public Response<UserDto> signup(@RequestBody @Valid UserSignupRequest userSignupRequest) {

        String validator = userSignupRequest.validate();
        if(validator != null) {
            return Response.<UserDto>badRequest()
                    .setPayload(null)
                    .setMsg("bad parameters : " + validator);
        }

        try {
            return Response.<UserDto>ok().setPayload(registerUser(userSignupRequest));
        } catch (DuplicateEntityException e){
            return Response.<UserDto>duplicateEntity()
                    .setPayload(null)
                    .setMsg(e.getMessage());
        } catch (Exception e) {
            return Response.<UserDto>internal()
                    .setPayload(null)
                    .setMsg("an error accrued while signing up");
        }
    }




    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Exception> handleAllExceptions(RuntimeException ex) {
        return new ResponseEntity<Exception>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }



    // -------------------------------------- private methods --------------------------------------

    /**
     * receive a request ro user registration, and register it
     * @param userSignupRequest the request
     * @throws DuplicateEntityException if user already exist
     * @return the user that was saved to the DB
     */
    private UserDto registerUser(UserSignupRequest userSignupRequest) throws DuplicateEntityException {
        UserDto userDto = new UserDto()
                //.setId(userSignupRequest.getId())
                .setUsername(userSignupRequest.getUsername())
                .setEmail(userSignupRequest.getEmail())
                .setPassword(userSignupRequest.getPassword());

        return userService.signup(userDto).setPassword("");
    }

}
