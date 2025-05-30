package com.nahmed.events;

import com.nahmed.driver.Driver;
import com.nahmed.enums.ConfigProperties;
import com.nahmed.utils.PropertyUtils;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import com.nahmed.reports.ExtentReport;
import com.nahmed.reports.ExtentManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hooks {
    private static final Logger LOGGER = LoggerFactory.getLogger(Hooks.class);

    @Before(order = 1) // Ensure order is appropriate if you have multiple @Before hooks
    public void setUp(Scenario scenario) {
        LOGGER.info("HOOKS @Before: Scenario - {}", scenario.getName());
        String browser = PropertyUtils.getValue(ConfigProperties.BROWSER);
        Driver.initDriver(browser);

        // Create ExtentTest for the current scenario
        ExtentReport.createTest(scenario.getName());
    }

    @After(order = 1) // Ensure order is appropriate
    public void tearDown(Scenario scenario) {
        LOGGER.info("HOOKS @After: Scenario - {}", scenario.getName());

        Driver.quitDriver();

        // Unload ExtentTest for the current thread
        ExtentManager.unloadTest();
    }

}