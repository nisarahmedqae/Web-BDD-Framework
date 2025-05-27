package com.nahmed.cucumber.utils;

public class AssertionService {

    public void assertEquals(String actual, String expected, String message) {
        if (!actual.equals(expected)) {
            throw new AssertionError(message + " Expected: " + expected + ", Actual: " + actual);
        }
    }

}
