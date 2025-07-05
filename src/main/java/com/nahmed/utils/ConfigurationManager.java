package com.nahmed.utils;

import com.nahmed.enums.ConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ConfigurationManager {

    private ConfigurationManager() {
    }

    private static final Logger LOG = LoggerFactory.getLogger(ConfigurationManager.class);
    private static final String ENV_SYSTEM_PROPERTY = "env"; // System property: -Denv=CERT
    private static final String currentEnvironment;

    // Static initializer block: runs once and is thread-safe
    static {
        String envFromProps = PropertyUtils.getValue(ConfigProperties.ENVIRONMENT);
        String defaultEnvironment = (envFromProps == null || envFromProps.trim().isEmpty())
                ? "INT" : envFromProps;

        currentEnvironment = "_" + System.getProperty(ENV_SYSTEM_PROPERTY, defaultEnvironment);
    }

    // Method to get the determined environment if needed elsewhere safely
    public static String getCurrentEnvironment() {
        return currentEnvironment;
    }

}
