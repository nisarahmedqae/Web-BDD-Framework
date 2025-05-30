package com.nahmed.cucumber.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter; // Or your preferred reporter
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.util.Objects;

import com.aventstack.extentreports.ExtentTest;
import org.slf4j.Logger; // Using SLF4J for logging, you can adapt to Log4j if you prefer
import org.slf4j.LoggerFactory;

public class ExtentManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExtentManager.class);
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> extentTestThreadLocal = new ThreadLocal<>(); // For scenario-specific test

    private ExtentManager() {
        // Private constructor to prevent instantiation
    }

    public static synchronized void initReports(String reportPath, String reportName, String documentTitle) {
        if (extent == null) {
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath + reportName + ".html");
            sparkReporter.config().setReportName(reportName);
            sparkReporter.config().setDocumentTitle(documentTitle);
            sparkReporter.config().setTheme(Theme.STANDARD); // Or Theme.DARK
            // Add any other configurations for your reporter

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);

            // You can add system information here if needed
            // extent.setSystemInfo("OS", System.getProperty("os.name"));
            // extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        }
    }

    public static synchronized ExtentTest createTest(String testName, String description) {
        if (Objects.isNull(extent)) {
            // Fallback or throw an error if initReports wasn't called
            // For simplicity, let's assume initReports is called externally (e.g., @BeforeSuite)
            // Or, you could have a default initialization here:
            // initReports("reports/extent/", "ExtentReport", "Test Execution Report");
            // However, it's better to control initialization explicitly.
            System.err.println("ExtentReports object is null. Call initReports() first.");
            return null; // Or throw an exception
        }
        ExtentTest test = extent.createTest(testName, description);
        extentTestThreadLocal.set(test); // VERY IMPORTANT: Store the created test for the current thread
        return test;
    }

    public static synchronized ExtentTest getExtentTest() {
        return extentTestThreadLocal.get(); // Retrieve the test for the current thread
    }

    public static void setExtentTest(ExtentTest test) {
        if (Objects.nonNull(test)) {
            LOGGER.debug("Setting ExtentTest for thread {}: {}", Thread.currentThread().getId(), test.getModel().getName());
        } else {
            LOGGER.warn("Attempting to set a null ExtentTest for thread {}. This might clear the current test.", Thread.currentThread().getId());
        }
        extentTestThreadLocal.set(test);
    }

    public static synchronized void unloadTest() { // Call this after each scenario
        extentTestThreadLocal.remove();
    }

    public static synchronized void flushReports() {
        if (extent != null) {
            extent.flush();
        }
    }

    public static void unload() {
        LOGGER.debug("ExtentManager.unload() called, typically at the end of a suite. Invoking unloadTest().");
        unloadTest(); // Delegates to the primary per-thread cleanup method
    }
}