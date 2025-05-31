package com.nahmed.events;

import com.nahmed.driver.Driver;
import com.nahmed.enums.ConfigProperties;
import com.nahmed.pageobjects.HomePage;
import com.nahmed.pageobjects.LoginPage;
import com.nahmed.reports.ExtentManager;
import com.nahmed.reports.ExtentReport; // Assuming this handles creating and setting the test in ExtentManager
import com.nahmed.utils.AssertionService;
import com.nahmed.utils.BrowserService;
import com.nahmed.utils.PropertyUtils;
import com.nahmed.utils.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll; // Import @AfterAll
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hooks {

    TestContext testContext;
    private static final Logger LOG = LoggerFactory.getLogger(Hooks.class);

    public Hooks() {
        this.testContext = new TestContext();
    }

    @Before(order = 1)
    public void setUp() {
        String environment = testContext.getCurrentEnvironment();
        if (environment.contains("int")) {
            LOG.info("INTEGRATION environment selected");
        } else if (environment.contains("cert")) {
            LOG.info("CERTIFICATION environment selected");
        }

        String browser = PropertyUtils.getValue(ConfigProperties.BROWSER);
        Driver.initDriver(browser);
    }

    @After(order = 1)
    public void tearDown() {
        Driver.quitDriver();
    }
}