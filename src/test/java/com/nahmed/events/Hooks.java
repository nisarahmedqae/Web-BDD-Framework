package com.nahmed.events;

import com.nahmed.driver.Driver;
import com.nahmed.enums.ConfigProperties;
import com.nahmed.reports.ExtentManager;
import com.nahmed.reports.ExtentReport; // Assuming this handles creating and setting the test in ExtentManager
import com.nahmed.utils.PropertyUtils;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll; // Import @AfterAll
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hooks {
    private static final Logger LOGGER = LoggerFactory.getLogger(Hooks.class);

    @Before(order = 1)
    public void setUp(Scenario scenario) {
        LOGGER.info("HOOKS @Before: Scenario - {}", scenario.getName());
        String browser = PropertyUtils.getValue(ConfigProperties.BROWSER);
        Driver.initDriver(browser);
    }

    @After(order = 1)
    public void tearDown(Scenario scenario) {
        LOGGER.info("HOOKS @After: Scenario - {}", scenario.getName());
        Driver.quitDriver();
    }
}