package com.nahmed.reports;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
// Removed ExtentSparkReporter and Theme imports as ExtentManager now handles initialization
import com.nahmed.constants.FrameworkConstants;
import com.nahmed.enums.CategoryType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ExtentReport {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExtentReport.class);
	// No longer need 'private static ExtentReports extent;' here, as ExtentManager manages it.

	private ExtentReport() {
	}

	// initReports() is removed from here as TestRunner calls ExtentManager.initReports()

	public static void flushReports() {
		// This method in ExtentReport might become redundant if TestRunner directly calls ExtentManager.flushReports()
		// For now, let it delegate.
		LOGGER.info("ExtentReport.flushReports() called, delegating to ExtentManager.flushReports().");
		ExtentManager.flushReports(); // Delegate to ExtentManager

		// Keep the Desktop.browse logic if you want ExtentReport to handle opening the report
		String reportFilePath = FrameworkConstants.getExtentReportFilePath(); // Ensure this path matches what ExtentManager used
		if (reportFilePath != null && !reportFilePath.trim().isEmpty()) {
			try {
				File reportFile = new File(reportFilePath);
				if (reportFile.exists()) {
					if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
						LOGGER.info("Attempting to open report: {}", reportFilePath);
						Desktop.getDesktop().browse(reportFile.toURI());
					} else {
						LOGGER.warn("Desktop browse action is not supported. Cannot open report automatically.");
					}
				} else {
					LOGGER.warn("Report file does not exist, cannot open: {}", reportFilePath);
				}
			} catch (IOException e) {
				LOGGER.error("Error opening report file: {}", reportFilePath, e);
			}
		}
	}

	public static void createTest(String testcasename) {
		ExtentReports reportsInstance = ExtentManager.getReports(); // Get the globally initialized instance
		if (Objects.nonNull(reportsInstance)) {
			LOGGER.debug("Creating ExtentTest in ExtentReport for: {}", testcasename);
			ExtentTest test = reportsInstance.createTest(testcasename);
			ExtentManager.setExtentTest(test); // This is crucial
		} else {
			LOGGER.error("ExtentReports instance is null (obtained from ExtentManager). " +
					"Cannot create test: {}. Ensure ExtentManager.initReports() was called in @BeforeSuite.", testcasename);
		}
	}

	public static void addAuthors(String[] authors) {
		ExtentTest currentTest = ExtentManager.getExtentTest();
		if (Objects.nonNull(currentTest)) {
			for (String author : authors) {
				if (author != null && !author.trim().isEmpty()) {
					currentTest.assignAuthor(author);
				}
			}
		} else {
			LOGGER.warn("Cannot assign authors. ExtentTest from ExtentManager is null.");
		}
	}

	public static void addCategories(CategoryType[] categories) {
		ExtentTest currentTest = ExtentManager.getExtentTest();
		if (Objects.nonNull(currentTest)) {
			for (CategoryType category : categories) {
				if (category != null) {
					currentTest.assignCategory(category.toString());
				}
			}
		} else {
			LOGGER.warn("Cannot assign categories. ExtentTest from ExtentManager is null.");
		}
	}
}