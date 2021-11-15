package com.project.reddital_backend.repositories;

import com.project.reddital_backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository  extends JpaRepository<User, Long> {
}
