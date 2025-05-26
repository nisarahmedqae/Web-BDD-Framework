package com.nahmed.cucumber.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.CucumberOptions.SnippetType;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        plugin = {"pretty:reports/cucumber/cucumber.txt",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "html:reports/cucumber/report.html",
                "json:reports/cucumber/cucumber.json",
                "com.nahmed.cucumber.utils.TestListener"
        },
        features = {"src/main/java/com/nahmed/cucumber/features"},
        glue = {"com.nahmed.cucumber.stepdefs",
        "com.nahmed.cucumber.events"},
        monochrome = true,
        snippets = SnippetType.CAMELCASE,
        tags = "@bookerAPI"
)

public class TestRunner extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}