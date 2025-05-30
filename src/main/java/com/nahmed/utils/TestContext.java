package com.nahmed.utils;

import com.nahmed.enums.ConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class TestContext {

    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(TestContext.class);

    public Map<String, Object> sessionMap = new HashMap<>();

    // Constants for environment selection
    private static final String ENV_SYSTEM_PROPERTY = "env"; // System property: -Denv=CERT

    // Store the determined environment for potential use elsewhere (like Hooks)
    private String currentEnvironment = null;

    // Method to get the determined environment if needed elsewhere safely
    public String getCurrentEnvironment() {
        if (currentEnvironment == null) {
            String envFromProps = PropertyUtils.getValue(ConfigProperties.ENVIRONMENT);
            String defaultEnvironment = (envFromProps == null || envFromProps.trim().isEmpty())
                    ? "INT"
                    : envFromProps;

            this.currentEnvironment = "_" + System.getProperty(ENV_SYSTEM_PROPERTY, defaultEnvironment);
            LOG.info("Determined target environment: " + this.currentEnvironment);
        }
        return currentEnvironment;
    }

}