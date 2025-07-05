package com.nahmed.utils;

import org.testng.Assert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AssertionService {

    public static void assertDigitCount(String input, int expectedDigitCount) {
        Assert.assertNotNull(input, "String should not be null");
        String regex = "^\\d{" + expectedDigitCount + "}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        Assert.assertTrue(matcher.matches(),
                "Expected string to have exactly " + expectedDigitCount + " digits, but was: " + input);
    }

    public static void assertStringLength(String input, int expectedLength) {
        Assert.assertNotNull(input, "String should not be null");
        Assert.assertEquals(input.length(), expectedLength,
                "Expected string length: " + expectedLength + ", but was: " + input.length());
    }

    public static void assertStringLengthGreaterThan(String input, int minLength) {
        Assert.assertNotNull(input, "String should not be null");
        Assert.assertTrue(input.length() > minLength,
                "Expected string length to be greater than " + minLength + ", but was: " + input.length());
    }

    public static void assertStringContains(String input, String expectedSubstring) {
        Assert.assertNotNull(input, "String should not be null");
        Assert.assertTrue(input.contains(expectedSubstring),
                "Expected string to contain \"" + expectedSubstring + "\", but was: \"" + input + "\"");
    }

}
