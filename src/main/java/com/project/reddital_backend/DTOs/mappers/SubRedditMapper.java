package com.project.reddital_backend.DTOs.mappers;

import com.project.reddital_backend.DTOs.models.SubRedditDto;
import com.project.reddital_backend.models.SubReddit;
import org.springframework.stereotype.Component;

@Component
public class SubRedditMapper {

    public static SubRedditDto toSubRedditDto(SubReddit sub) {

        if(sub == null)
            return null;

        return SubRedditDto.builder()
                .id(sub.getId())
                .name(sub.getName())
                .build();
    }
}
