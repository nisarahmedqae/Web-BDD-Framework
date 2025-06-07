package com.nahmed.reports;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.nahmed.constants.FrameworkConstants;

public final class ExtentReport {

    private ExtentReport() {

    }

    private static ExtentReports extent;

    public static void initReports() {
        if (Objects.isNull(extent)) {
            extent = new ExtentReports();
            ExtentSparkReporter spark = new ExtentSparkReporter(FrameworkConstants.getExtentReportFilePath());
            extent.attachReporter(spark);
            spark.config().setTheme(Theme.STANDARD);
            spark.config().setDocumentTitle("Test Results");
            spark.config().setReportName("Web-BDD-Framework");
        }
    }

    public static void flushReports() {
        if (Objects.nonNull(extent)) {
            extent.flush();
        }
        ExtentManager.unload(); // Unloads ExtentManager's context
        ExtentStepManager.unload(); // Clean up scenario context
        try {
            Desktop.getDesktop().browse(new File(FrameworkConstants.getExtentReportFilePath()).toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createTest(String testCaseName) {
        ExtentTest scenarioTest = extent.createTest(testCaseName);
        ExtentStepManager.setExtentTestStep(scenarioTest);
        ExtentManager.setExtentTest(scenarioTest); // Set scenario as current test in ExtentManager
    }

    public static void addTestStep(String stepDescription) {
        ExtentTest currentScenario = ExtentStepManager.getExtentTestStep();
        if (currentScenario != null) {
            ExtentTest stepTest = currentScenario.createNode(stepDescription);
            ExtentManager.setExtentTest(stepTest); // Now, ExtentManager's context is this step
        } else {
            System.err.println("ERROR: Cannot add step '" + stepDescription + "'. Current scenario test not found in ExtentReport.");
        }
    }

}
