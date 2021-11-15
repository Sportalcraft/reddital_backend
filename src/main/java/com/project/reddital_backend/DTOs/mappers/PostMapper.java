package com.project.reddital_backend.DTOs.mappers;

import com.project.reddital_backend.DTOs.models.PostDto;
import com.project.reddital_backend.models.Post;

public class PostMapper {

    public static PostDto toPostDto(Post post) {

        if(post == null)
            return null;

        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .time(post.getTime())
                .subredditName(post.getSubreddit().getName())
                .username(post.getUser().getUsername())
                .build();
    }
}
