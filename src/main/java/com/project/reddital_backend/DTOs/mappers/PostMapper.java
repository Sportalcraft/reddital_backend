package com.project.reddital_backend.DTOs.mappers;

import com.project.reddital_backend.DTOs.models.PostDto;
import com.project.reddital_backend.controllers.requests.PostingRequest;
import com.project.reddital_backend.models.Post;

public class PostMapper {

    public static PostDto toPostDto(Post post) {

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

    public static PostDto toPostDto(PostingRequest postingRequest) {

        if(postingRequest == null)
            return null;

        return PostDto.builder()
                .title(postingRequest.getTitle())
                .content(postingRequest.getContent())
                .subReddit(postingRequest.getSubReddit())
                .username(authenticate(postingRequest.getAuthenticationKey()))
                .build();
    }

    // ------------------------------------ private methods --------------------------------------

    private static String authenticate(String authenticationKey) {
        return "tal";
    }
}
