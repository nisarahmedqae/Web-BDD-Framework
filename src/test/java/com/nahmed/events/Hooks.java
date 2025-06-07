package com.nahmed.events;

import com.nahmed.driver.Driver;
import com.nahmed.enums.ConfigProperties;
import com.nahmed.utils.PropertyUtils;
import com.nahmed.utils.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
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