package com.project.reddital_backend.controllers.api;

import com.project.reddital_backend.DTOs.mappers.SubRedditMapper;
import com.project.reddital_backend.DTOs.models.SubRedditDto;
import com.project.reddital_backend.controllers.requests.AddSubRequest;
import com.project.reddital_backend.services.SubRedditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@CrossOrigin
@RequestMapping("/subredditals")
public class SubRedditalController {

    @Autowired
    private SubRedditService subRedditService;

    @Autowired
    private SubRedditMapper subRedditMapper;


    @CrossOrigin
    @PostMapping("/create")
    public ResponseEntity<SubRedditDto> createSub(@RequestBody AddSubRequest request) {

        request.validate();

        return ResponseEntity.created(URI.create("subredditals/create"))
                .body(create(request));
    }


    // ------------------------------------------- private methods -------------------------------------------

    private SubRedditDto create(AddSubRequest request) {
        return subRedditService.create(subRedditMapper.toSubRedditDto(request));
    }
}
