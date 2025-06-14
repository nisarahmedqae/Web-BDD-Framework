package com.nahmed.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.CucumberOptions.SnippetType;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;

@CucumberOptions(
        plugin = {"pretty:reports/cucumber/cucumber.txt",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "html:reports/cucumber/report.html",
                "json:reports/cucumber/cucumber.json",
                "rerun:reports/cucumber/rerun.txt",
                "com.nahmed.listeners.TestListener"
        },
        features = {"@reports/cucumber/rerun.txt"},
        glue = {"com.nahmed.stepdefinitions",
                "com.nahmed.events"},
        monochrome = true,
        snippets = SnippetType.CAMELCASE,
        tags = "@login"
)

@Listeners(com.nahmed.listeners.AnnotationTransformer.class)
public class FailedTestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }

}