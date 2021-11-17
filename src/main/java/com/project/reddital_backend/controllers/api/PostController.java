package com.project.reddital_backend.controllers.api;

import com.project.reddital_backend.DTOs.mappers.PostMapper;
import com.project.reddital_backend.DTOs.models.PostDto;
import com.project.reddital_backend.controllers.requests.PostingRequest;
import com.project.reddital_backend.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.net.URI;

@RestController
@CrossOrigin
@RequestMapping("/")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private PostMapper postMapper;


    @CrossOrigin
    @PostMapping("/posting")
    public ResponseEntity<PostDto> signup(@RequestBody @Valid PostingRequest postingRequest, @PathParam("subreddital") String subreddital) {
        postingRequest.validate();

        return ResponseEntity.created(URI.create("/posting/" + subreddital))
                .body(posting(postingRequest, subreddital));
    }


    // ----------------------------------------- private methods ----------------------------------------------

    /**
     * post a post
     * @param toPost the posting request
     * @param subreddital the subreddit to post to
     * @return the posted post's dto
     */
    private PostDto posting(PostingRequest toPost, String subreddital) {
        return postService.posting(postMapper.toPostDto(toPost, subreddital));
    }

}
