package com.project.reddital_backend.services;

import com.project.reddital_backend.repositories.PostRepository;
import com.project.reddital_backend.repositories.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class PostService {

    @Autowired
    private PostRepository postRepository; // access DB for post operations




}
