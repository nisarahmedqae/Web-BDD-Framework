package com.nahmed.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.CucumberOptions.SnippetType;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "html:reports/cucumber/bdd_report.html",
                "rerun:reports/cucumber/rerun_data.txt",
                "com.nahmed.listeners.TestListener"
        },
        features = {"@reports/cucumber/rerun_data.txt"},
        glue = {"com.nahmed.stepdefinitions",
                "com.nahmed.events"},
        monochrome = true,
        snippets = SnippetType.CAMELCASE
)

public class FailedTestRunner extends AbstractTestNGCucumberTests {

    // 1. Parallel Execution Control
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }

    // 2. Thread Count Configuration
    static {
        System.setProperty("dataproviderthreadcount", "1");
    }

}