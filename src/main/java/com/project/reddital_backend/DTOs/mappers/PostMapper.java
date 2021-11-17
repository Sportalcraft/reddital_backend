package com.project.reddital_backend.DTOs.mappers;

import com.project.reddital_backend.DTOs.models.PostDto;
import com.project.reddital_backend.DTOs.models.UserDto;
import com.project.reddital_backend.controllers.requests.PostingRequest;
import com.project.reddital_backend.models.Post;
import com.project.reddital_backend.models.User;
import com.project.reddital_backend.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    @Autowired
    private AuthenticationService authenticationService;

    /**
     * transform a post object into a post dto
     * @param post the post object
     * @return the mapping of the post object to post dto
     */
    public PostDto toPostDto(Post post) {

        if(post == null)
            return null;

        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .time(post.getTime().getTime())
                .subReddit(post.getSubreddit().getName())
                .username(post.getUser().getUsername())
                .build();
    }

    /**
     * transform a posting request object into a post dto. transform authentication key into a user.
     * @param postingRequest the posting request
     * @return the mapping of the posting request to post dto
     */
    public PostDto toPostDto(PostingRequest postingRequest) {

        if(postingRequest == null)
            return null;

        String key = postingRequest.getAuthenticationKey();
        UserDto user = authenticationService.authenticate(key);
        String userName = user == null? null : user.getUsername();

        return PostDto.builder()
                .title(postingRequest.getTitle())
                .content(postingRequest.getContent())
                .subReddit(postingRequest.getSubReddit())
                .username(userName)
                .build();
    }
}
