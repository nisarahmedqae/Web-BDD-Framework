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
import com.nahmed.enums.CategoryType;

public final class ExtentReport {

	private ExtentReport() {

	}

	private static ExtentReports extent;

	// ThreadLocal to hold the ExtentTest instance for the current scenario
	private static ThreadLocal<ExtentTest> scenarioTestThreadLocal = new ThreadLocal<>();

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
		scenarioTestThreadLocal.remove(); // Clean up scenario context
		try {
			Desktop.getDesktop().browse(new File(FrameworkConstants.getExtentReportFilePath()).toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void createTest(String testCaseName) {
		ExtentTest scenarioTest = extent.createTest(testCaseName);
		scenarioTestThreadLocal.set(scenarioTest);
		ExtentManager.setExtentTest(scenarioTest); // Set scenario as current test in ExtentManager
	}

	public static void addStep(String stepDescription) {
		ExtentTest currentScenario = scenarioTestThreadLocal.get();
		if (currentScenario != null) {
			ExtentTest stepTest = currentScenario.createNode(stepDescription);
			ExtentManager.setExtentTest(stepTest); // Now, ExtentManager's context is this step
		} else {
			System.err.println("ERROR: Cannot add step '" + stepDescription + "'. Current scenario test not found in ExtentReport.");
		}
	}

	public static ExtentTest getCurrentScenarioTest() {
		return scenarioTestThreadLocal.get();
	}

	public static void addAuthors(String[] authors) {
		ExtentTest scenario = scenarioTestThreadLocal.get(); // Get the scenario test
		if (scenario != null) {
			for (String temp : authors) {
				scenario.assignAuthor(temp);
			}
		} else {
			System.err.println("ERROR: Cannot add authors. Current scenario test not found.");
		}
	}

	public static void addCategories(CategoryType[] categories) {
		ExtentTest scenario = scenarioTestThreadLocal.get(); // Get the scenario test
		if (scenario != null) {
			for (CategoryType temp : categories) {
				scenario.assignCategory(temp.toString());
			}
		} else {
			System.err.println("ERROR: Cannot add categories. Current scenario test not found.");
		}
	}

}
