package com.nahmed.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter; // Or your preferred reporter
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public final class ExtentManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExtentManager.class);
    private static ExtentReports extentReportsInstance; // Holds the single ExtentReports instance
    private static final ThreadLocal<ExtentTest> extentTestThreadLocal = new ThreadLocal<>();

    private ExtentManager() {
    }

    public static synchronized void initReports(String reportPath, String reportName, String documentTitle) {
        if (extentReportsInstance == null) {
            LOGGER.info("Initializing ExtentReports via ExtentManager...");
            extentReportsInstance = new ExtentReports();
            // Assuming FrameworkConstants provides the full path including .html
            // If not, adjust path construction: e.g., reportPath + reportName + ".html"
            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath); // Use the path from TestRunner

            // Configure Spark Reporter
            spark.config().setTheme(Theme.STANDARD);
            spark.config().setDocumentTitle(documentTitle); // Use title from TestRunner
            spark.config().setReportName(reportName);       // Use name from TestRunner

            extentReportsInstance.attachReporter(spark);
            LOGGER.info("ExtentReports initialized by ExtentManager. Report will be saved to: {}", reportPath);
        } else {
            LOGGER.info("ExtentReports already initialized (ExtentManager).");
        }
    }

    /**
     * Returns the globally initialized ExtentReports instance.
     * @return The ExtentReports instance, or null if initReports was not called.
     */
    public static ExtentReports getReports() {
        if (extentReportsInstance == null) {
            LOGGER.error("ExtentReports instance is null in ExtentManager. Ensure ExtentManager.initReports() was called.");
        }
        return extentReportsInstance;
    }

    public static ExtentTest getExtentTest() {
        if (Objects.isNull(extentTestThreadLocal.get())) {
            LOGGER.warn("ExtentTest object is null for the current thread in ExtentManager. " +
                    "Ensure ExtentReport.createTest() (which calls ExtentManager.setExtentTest()) was invoked in @Before hook.");
        }
        return extentTestThreadLocal.get();
    }

    public static void setExtentTest(ExtentTest test) {
        if (Objects.nonNull(test)) {
            LOGGER.debug("Setting ExtentTest in ExtentManager for thread {}: {}", Thread.currentThread().getId(), test.getModel().getName());
        } else {
            LOGGER.warn("Attempting to set a null ExtentTest in ExtentManager for thread {}.", Thread.currentThread().getId());
        }
        extentTestThreadLocal.set(test);
    }

    public static void unloadTest() {
        if (Objects.nonNull(extentTestThreadLocal.get())) {
            LOGGER.debug("Unloading ExtentTest from ExtentManager for thread {}: {}", Thread.currentThread().getId(), extentTestThreadLocal.get().getModel().getName());
        } else {
            LOGGER.debug("No ExtentTest to unload in ExtentManager for thread {}.", Thread.currentThread().getId());
        }
        extentTestThreadLocal.remove();
    }

    public static void flushReports() {
        if (extentReportsInstance != null) {
            LOGGER.info("Flushing ExtentReports via ExtentManager...");
            extentReportsInstance.flush();
            LOGGER.info("ExtentReports flushed by ExtentManager.");
        } else {
            LOGGER.warn("ExtentReports instance was null in ExtentManager. Cannot flush.");
        }
        // The unload() for ThreadLocal is typically per scenario (unloadTest),
        // but a final cleanup here doesn't hurt if unloadTest wasn't called.
        unloadTest();
    }
}