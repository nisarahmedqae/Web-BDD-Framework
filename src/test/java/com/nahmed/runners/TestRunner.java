package com.nahmed.runners;

import com.nahmed.listeners.RetryFailedTests;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.CucumberOptions.SnippetType;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@CucumberOptions(
        plugin = {"pretty:reports/cucumber/cucumber.txt",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "html:reports/cucumber/report.html",
                "json:reports/cucumber/cucumber.json",
                "com.nahmed.listeners.TestListener"
        },
        features = {"src/test/java/com/nahmed/features"},
        glue = {"com.nahmed.stepdefinitions",
                "com.nahmed.events"},
        monochrome = true,
        snippets = SnippetType.CAMELCASE,
        tags = "@login"
)

@Listeners(com.nahmed.listeners.AnnotationTransformer.class)
public class TestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }

}