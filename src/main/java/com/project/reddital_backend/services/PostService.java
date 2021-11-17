package com.project.reddital_backend.services;

import com.project.reddital_backend.DTOs.mappers.PostMapper;
import com.project.reddital_backend.DTOs.models.PostDto;
import com.project.reddital_backend.exceptions.BadParametersException;
import com.project.reddital_backend.exceptions.UnauthorizedException;
import com.project.reddital_backend.models.Post;
import com.project.reddital_backend.models.SubReddit;
import com.project.reddital_backend.models.User;
import com.project.reddital_backend.repositories.PostRepository;
import com.project.reddital_backend.repositories.SubRedditRepository;
import com.project.reddital_backend.repositories.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Getter
@Setter
public class PostService {

    // ------------------------------------------------------- properties -------------------------------------------------------

    @Autowired
    private PostRepository postRepository; // access DB for post operations

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubRedditRepository subRedditRepository;

    @Autowired
    private PostMapper postMapper;


    // ------------------------------------------------------- methods -------------------------------------------------------

    /**
     * post a post
     * @param postDto the post to be posted
     * @return the posted post object
     */
    public PostDto posting(PostDto postDto) {
        Optional<User> user = getOptional(userRepository.findByUsername(postDto.getUsername()));
        Optional<SubReddit> sub = getOptional(subRedditRepository.findByName(postDto.getSubReddit()));

        if(user.isEmpty()) {  // there is no such user, error!
            throw new UnauthorizedException("you do not have permission to post here!");
        }

        if(sub.isEmpty()) {  // there is no such user, error!
            throw new BadParametersException("no such sub exists : " + postDto.getSubReddit());
        }

        //saving the post
        Post post = Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .subreddit(sub.get())
                .user(user.get())
                .build();

        // returned saved post
        return postMapper.toPostDto(postRepository.save(post));
    }





    // ------------------------------------------------------- private methods -------------------------------------------------------

    /**
     * get an optional of a given item
     * @param item the item
     * @return an optional of the item
     */
    private <T> Optional<T> getOptional(T item) {
        return Optional.ofNullable(item);
    }
}
