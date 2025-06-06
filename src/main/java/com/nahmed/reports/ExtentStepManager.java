package com.nahmed.reports;

import com.aventstack.extentreports.ExtentTest;

public final class ExtentStepManager {

    private ExtentStepManager() {

    }

    private static ThreadLocal<ExtentTest> extTestStep = new ThreadLocal<>();

    static ExtentTest getExtentTestStep() {
        return extTestStep.get();
    }

    public static void setExtentTestStep(ExtentTest test) {
        extTestStep.set(test);
    }

    static void unload() {
        extTestStep.remove();
    }

}
