package com.project.reddital_backend.controllers.requests;

import com.project.reddital_backend.exceptions.BadParametersException;
import com.project.reddital_backend.exceptions.UnauthorizedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class PostingRequestTest {

    @Test
    @DisplayName("test validate good")
    public void validate_good(){
        Request postingRequest = PostingRequest.builder()
                .title("hello world!")
                .content("just came to say hello!")
                .authenticationKey("1")
                .build();

        postingRequest.validate();
    }

    @Test
    @DisplayName("test validate bad title")
    public void validate_title(){

        PostingRequest postingRequest = PostingRequest.builder()
                .title("")
                .content("just came to say hello!")
                .authenticationKey("1")
                .build();

        assertThrows(BadParametersException.class, postingRequest::validate);

        postingRequest.setTitle(null);
        assertThrows(BadParametersException.class, postingRequest::validate);
    }

    @Test
    @DisplayName("test validate bad content")
    public void validate_content(){

        PostingRequest postingRequest = PostingRequest.builder()
                .title("hello world!")
                .content("")
                .authenticationKey("1")
                .build();

        assertThrows(BadParametersException.class, postingRequest::validate);

        postingRequest.setContent(null);
        assertThrows(BadParametersException.class, postingRequest::validate);
    }

    @Test
    @DisplayName("test validate bad authentication key")
    public void validate_authenticationkey(){

        PostingRequest postingRequest = PostingRequest.builder()
                .title("hello world!")
                .content("just came to say hello!")
                .authenticationKey("")
                .build();

        assertThrows(UnauthorizedException.class, postingRequest::validate);

        postingRequest.setAuthenticationKey(null);
        assertThrows(UnauthorizedException.class, postingRequest::validate);
    }
}
