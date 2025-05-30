package com.nahmed.cucumber.events; // Or your actual package

import com.nahmed.cucumber.driver.Driver;
import com.nahmed.cucumber.driver.DriverManager;
import com.nahmed.cucumber.enums.ConfigProperties;
import com.nahmed.cucumber.reports.ExtentManager;
// import com.nahmed.cucumber.reports.ExtentLogger; // Not usually needed directly in Hooks for test creation
import com.nahmed.cucumber.utils.PropertyUtils;
import com.nahmed.cucumber.utils.ScreenshotUtils;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Hooks {
    private static final Logger LOG = LogManager.getLogger(Hooks.class);

    @Before(order = 0) // Ensure this order is before other @Before hooks if necessary
    public void setUp(Scenario scenario) {
        LOG.info("HOOKS: @Before hook running for scenario: " + scenario.getName());

        // 1. Initialize WebDriver (You are already doing this)
        String browser = PropertyUtils.getValue(ConfigProperties.BROWSER);
        Driver.initDriver(browser);
        LOG.info("WebDriver initialized for browser: " + browser);

        // 2. Create ExtentTest for the current scenario
        // Ensure ExtentManager.initReports() has been called (e.g., in a @BeforeSuite listener or a static initializer)
        // For this example, I'll assume initReports is handled elsewhere or you add a check.
        // If ExtentManager.extent is null, createTest might fail or return null.
        ExtentManager.createTest(scenario.getName(), "Tags: " + String.join(", ", scenario.getSourceTagNames()));
        LOG.info("ExtentTest created for scenario: " + scenario.getName());
    }

    @After(order = 1) // Ensure this order is appropriate
    public void tearDown(Scenario scenario) {
        LOG.info("HOOKS: @After hook running for scenario: " + scenario.getName());

        if (scenario.isFailed()) {
            LOG.error("Scenario FAILED: " + scenario.getName());
            // Add screenshot to Extent Report
            if (DriverManager.getDriver() instanceof TakesScreenshot) {
                String screenshotBase64 = ScreenshotUtils.getBase64Image(); // Assuming ScreenshotUtils returns base64
                if (ExtentManager.getExtentTest() != null) { // Check if test object exists
                    ExtentManager.getExtentTest().addScreenCaptureFromBase64String(screenshotBase64, "Screenshot on failure");
                    // Or if you use ExtentLogger:
                    // ExtentLogger.addScreenShot(screenshotBase64, "Screenshot on failure");
                } else {
                    LOG.warn("ExtentTest object was null. Cannot attach screenshot for scenario: " + scenario.getName());
                }
            }
        } else {
            LOG.info("Scenario PASSED: " + scenario.getName());
        }

        // Quit WebDriver
        Driver.quitDriver();
        LOG.info("WebDriver quit.");

        // Unload ExtentTest for the current thread
        ExtentManager.unloadTest();
        LOG.info("ExtentTest unloaded for scenario: " + scenario.getName());
    }

    // You might need a mechanism to call ExtentManager.initReports() and ExtentManager.flushReports()
    // For example, using TestNG Listeners:
    // @org.testng.annotations.BeforeSuite
    // public void beforeSuite() {
    //     ExtentManager.initReports("reports/extent/", "AutomationReport", "Test Execution Results");
    // }
    //
    // @org.testng.annotations.AfterSuite
    // public void afterSuite() {
    //     ExtentManager.flushReports();
    // }
    // If you are not using TestNG suite listeners, you can use Cucumber's @BeforeAll and @AfterAll
    // (requires cucumber-jvm version that supports them well, typically with JUnit 5 platform).
}