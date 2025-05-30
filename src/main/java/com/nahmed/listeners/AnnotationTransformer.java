package com.nahmed.listeners;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class AnnotationTransformer implements IAnnotationTransformer {

    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        // This will apply the RetryFailedTests analyzer to all @Test methods
        // that TestNG encounters, including those generated for Cucumber scenarios.
        annotation.setRetryAnalyzer(RetryFailedTests.class);
    }
}