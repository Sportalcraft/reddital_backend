package com.project.reddital_backend.controllers.api;

import com.project.reddital_backend.DTOs.mappers.PostMapper;
import com.project.reddital_backend.DTOs.models.PostDto;
import com.project.reddital_backend.DTOs.models.UserDto;
import com.project.reddital_backend.controllers.requests.PostingRequest;
import com.project.reddital_backend.controllers.requests.SignupRequest;
import com.project.reddital_backend.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@CrossOrigin
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;


    @CrossOrigin
    @PostMapping("/posting")
    public ResponseEntity<PostDto> signup(@RequestBody @Valid PostingRequest postingRequest) {
        postingRequest.validate();

        return ResponseEntity.created(URI.create("/post/posting"))
                .body(posting(postingRequest));
    }


    // ----------------------------------------- private methods ----------------------------------------------

    private PostDto posting(PostingRequest toPost) {
        return postService.posting(PostMapper.toPostDto((toPost)));
    }

}
