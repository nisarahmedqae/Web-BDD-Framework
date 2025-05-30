package com.nahmed.cucumber.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.CucumberOptions.SnippetType;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import com.nahmed.cucumber.reports.ExtentManager;

@CucumberOptions(
        plugin = {"pretty:reports/cucumber/cucumber.txt",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "html:reports/cucumber/report.html",
                "json:reports/cucumber/cucumber.json",
                "com.nahmed.cucumber.listeners.TestListener"
                //"com.nahmed.cucumber.listeners.ListenerClass"
        },
        features = {"src/test/java/com/nahmed/cucumber/features"},
        glue = {"com.nahmed.cucumber.stepdefinitions",
                "com.nahmed.cucumber.events"},
        monochrome = true,
        snippets = SnippetType.CAMELCASE,
        tags = "@login"
)

@Listeners(com.nahmed.cucumber.listeners.AnnotationTransformer.class)
public class TestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }

    @BeforeSuite(alwaysRun = true)
    public void initializeExtentReport() {
        ExtentManager.initReports("reports/extent/", "AutomationReport", "Test Execution Results");
        // You can get path/name from config properties if needed
    }

    @AfterSuite(alwaysRun = true)
    public void tearDownExtentReport() {
        ExtentManager.flushReports();
    }

}