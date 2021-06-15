package com.bank.api.regextests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegexTests {
    private String regex = "users/+\\d";
    @Test
    void regex_expectedTrue(){
        String actual = "users/1";
        assertTrue(actual.matches(regex));
    }

    @Test
    void regex_expectedFalse(){
        String actual = "users/1/";
        assertFalse(actual.matches(regex));
    }

    @Test
    void regex_idIsNotNumber_expectedFalse(){
        String actual = "users/123dfsd";
        assertFalse(actual.matches(regex));
    }

    @Test
    void regex_idIsNotNumber_expectedFalse2(){
        String actual = "users/ffff";
        assertFalse(actual.matches(regex));
    }
}
