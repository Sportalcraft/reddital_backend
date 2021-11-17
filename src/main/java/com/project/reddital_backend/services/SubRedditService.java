package com.project.reddital_backend.services;

import com.project.reddital_backend.DTOs.mappers.SubRedditMapper;
import com.project.reddital_backend.DTOs.models.SubRedditDto;
import com.project.reddital_backend.exceptions.DuplicateEntityException;
import com.project.reddital_backend.models.SubReddit;
import com.project.reddital_backend.models.User;
import com.project.reddital_backend.repositories.SubRedditRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    /**
     * create a new subreddit
     * @param toCreate the subreddit to create
     * @return the created subreddit
     */
    public SubRedditDto create(SubRedditDto toCreate) {
        Optional<SubReddit> sub = getOptional(getSubRedditRepository().findByName(toCreate.getName()));

        if (sub.isEmpty()) {
            //No such sub exit, create the new sub object and save it.

            SubReddit newSub = SubReddit.builder()
                    .name(toCreate.getName())
                    .build();

            return subRedditMapper.toSubRedditDto(subRedditRepository.save(newSub)); // save new sub
        }

        // sub already exist. throw exception.
        throw new DuplicateEntityException("Failed to create new subreddital, already exists : " + toCreate.getName());
    }


    // --------------------------------------------------- private methods ---------------------------------------------------


    /**
     * get an optional of a given item
     * @param item the item
     * @return an optional of the item
     */
    private <T> Optional<T> getOptional(T item) {
        return Optional.ofNullable(item);
    }
}
