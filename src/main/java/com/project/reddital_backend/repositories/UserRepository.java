package com.project.reddital_backend.repositories;

import com.project.reddital_backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * return a user by its unique primary key
     * @param id the PK to find the user by
     * @return the user by the given ID
     */
    User findById(long id);

    /**
     * return a user by its unique username
     * @param username the username to find the user by
     * @return the user by the given username
     */
    User findByUsername(String username);
}
