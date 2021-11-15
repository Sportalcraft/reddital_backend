package com.project.reddital_backend.DTOs.models;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(chain = true)
public class SubRedditDto {
    private long id;

    private String name;
}
