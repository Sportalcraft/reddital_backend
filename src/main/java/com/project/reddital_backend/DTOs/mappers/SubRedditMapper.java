package com.project.reddital_backend.DTOs.mappers;

import com.project.reddital_backend.DTOs.models.SubRedditDto;
import com.project.reddital_backend.controllers.requests.AddSubRequest;
import com.project.reddital_backend.models.SubReddit;
import org.springframework.stereotype.Component;

@Component
public class SubRedditMapper {

    /**
     * transform a Subreddit object to subreddit dto
     * @param sub the subreddit
     * @return the mapping of the subreddit into a dto
     */
    public SubRedditDto toSubRedditDto(SubReddit sub) {

        if(sub == null)
            return null;

        return SubRedditDto.builder()
                .id(sub.getId())
                .name(sub.getName())
                .build();
    }

    public SubRedditDto toSubRedditDto(AddSubRequest request) {
        if(request == null)
            return null;

        return SubRedditDto.builder()
                .name(request.getName())
                .build();
    }
}
