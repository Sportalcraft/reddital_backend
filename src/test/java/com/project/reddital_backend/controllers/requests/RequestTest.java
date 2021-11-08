package com.project.reddital_backend.controllers.requests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockitoExtension.class)
public class RequestTest {

    // a class that implements the abstract request class
    private static class RequestMock extends Request{

        @Override
        public String validate() {
            return null;
        }

        @Override
        public void clean() {
        }
    }



    // ---------------------------------------------- tests ----------------------------------------------

    @Test
    @DisplayName("test removeWhiteSpace")
    public void removeWhiteSpace() {
        assertEquals("", new RequestMock().removeWhiteSpace(""));
        assertEquals("", new RequestMock().removeWhiteSpace("     "));
        assertEquals("", new RequestMock().removeWhiteSpace("   \t \n   \t"));
        assertEquals("abcdefg", new RequestMock().removeWhiteSpace("  a b c d      ef g"));
        assertEquals("abcdefghi", new RequestMock().removeWhiteSpace("  a b c d      ef g  \t   h  \n\n \t i  "));
    }

    @Test
    @DisplayName("test removeWhiteSpace")
    public void nullOrEmpty() {
        assertTrue(new RequestMock().nullOrEmpty(null));
        assertTrue(new RequestMock().nullOrEmpty(""));
        assertFalse(new RequestMock().nullOrEmpty("abc"));
    }

}
