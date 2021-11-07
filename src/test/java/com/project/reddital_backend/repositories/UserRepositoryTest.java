package com.project.reddital_backend.repositories;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@Transactional
@RunWith(MockitoJUnitRunner.class)
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
