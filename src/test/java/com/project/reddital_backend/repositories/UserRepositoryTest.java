package com.project.reddital_backend.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@Transactional
@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {

    // ------------------------------------------------------- properties -------------------------------------------------------

    //@Autowired
    //private UserRepository userRepositoryUnderTest;


    // ------------------------------------------------------- tests -------------------------------------------------------
    @Test
    @DisplayName("test save new user")
    public void testSave() {

    }
}
