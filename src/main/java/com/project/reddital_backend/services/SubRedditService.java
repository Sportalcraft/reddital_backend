package com.project.reddital_backend.services;

import com.project.reddital_backend.DTOs.mappers.SubRedditMapper;
import com.project.reddital_backend.DTOs.models.SubRedditDto;
import com.project.reddital_backend.repositories.SubRedditRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class SubRedditService {

    @Autowired
    private SubRedditRepository subRedditRepository;

    @Autowired
    private SubRedditMapper subRedditMapper;

    /**
     * get a subreddit dto buy its name
     * @param name the name of the subreddit dto
     * @return the corresponding subreddit dto
     */
    public SubRedditDto findByName(String name) {
        return subRedditMapper.toSubRedditDto(subRedditRepository.findByName(name));
    }
}
