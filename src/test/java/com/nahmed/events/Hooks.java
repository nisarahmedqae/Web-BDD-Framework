package com.nahmed.events;

import com.nahmed.driver.Driver;
import com.nahmed.enums.ConfigProperties;
import com.nahmed.utils.ConfigurationManager;
import com.nahmed.utils.PropertyUtils;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hooks {

    private static final Logger LOG = LoggerFactory.getLogger(Hooks.class);

    @Before(order = 1)
    public void setUp() {
        String currentEnvironment = ConfigurationManager.getCurrentEnvironment();
        switch (currentEnvironment.toUpperCase()) {
            case "_INT":
                LOG.info("Environment selected: INTEGRATION");
                break;
            case "_CERT":
                LOG.info("Environment selected: CERTIFICATION");
                break;
            default:
                LOG.warn("Default environment selected: {}", currentEnvironment);
                break;
        }

        String browser = PropertyUtils.getValue(ConfigProperties.BROWSER);
        Driver.initDriver(browser);
    }

    @After(order = 1)
    public void tearDown() {
        Driver.quitDriver();
    }

}