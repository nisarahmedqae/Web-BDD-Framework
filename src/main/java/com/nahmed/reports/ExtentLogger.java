package com.nahmed.reports;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.nahmed.enums.ConfigProperties;
import com.nahmed.utils.PropertyUtils;
import com.nahmed.utils.ScreenshotUtils;

public final class ExtentLogger {

    private ExtentLogger() {

    }


    public static void pass(String message) {
        if (PropertyUtils.getValue(ConfigProperties.PASSED_STEPS_SCREENSHOTS).equalsIgnoreCase("yes")) {
            ExtentManager.getExtentTest().pass(message,
                    MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenshotUtils.getBase64Image()).build());
        } else {
            ExtentManager.getExtentTest().pass(message);
            ;
        }
    }

    public static void fail(String message) {
        if (PropertyUtils.getValue(ConfigProperties.FAILED_STEPS_SCREENSHOTS).equalsIgnoreCase("yes")) {
            ExtentManager.getExtentTest().fail(message,
                    MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenshotUtils.getBase64Image()).build());
        } else {
            ExtentManager.getExtentTest().fail(message);
        }
    }

    public static void skip(String message) {
        if (PropertyUtils.getValue(ConfigProperties.SKIPPED_STEPS_SCREENSHOTS).equalsIgnoreCase("yes")) {
            ExtentManager.getExtentTest().skip(message,
                    MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenshotUtils.getBase64Image()).build());
        } else {
            ExtentManager.getExtentTest().skip(message);
        }
    }

}
