package com.project.reddital_backend;

import com.project.reddital_backend.DTOs.mappers.UserMapperTest;
import com.project.reddital_backend.models.UserTest;
import com.project.reddital_backend.services.UserServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        UserServiceTest.class,
        UserMapperTest.class,
        UserTest.class,
})
public class AllUnitTests {
}
