package com.project.reddital_backend.DTOs.models;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Builder
@Accessors(chain = true)
public class PostDto {

    private long id;

    private String title;

    private String content;

    private Date time;

    private String username;

    private String subredditName;
}
