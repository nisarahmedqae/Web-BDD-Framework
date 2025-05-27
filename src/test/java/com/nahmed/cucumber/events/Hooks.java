package com.nahmed.cucumber.events;

import com.nahmed.cucumber.driver.Driver;
import com.nahmed.cucumber.enums.ConfigProperties;
import com.nahmed.cucumber.utils.PropertyUtils;
import com.nahmed.cucumber.utils.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class Hooks {

    private static final Logger LOG = LoggerFactory.getLogger(Hooks.class);
    private final TestContext context;

    public Hooks(TestContext context) {
        this.context = context;
    }

    @Before
    protected void setUp() {
        String browser = PropertyUtils.getValue(ConfigProperties.BROWSER);
        Driver.initDriver(browser);
    }

    @After
    protected void tearDown() {
        Driver.quitDriver();
    }


}