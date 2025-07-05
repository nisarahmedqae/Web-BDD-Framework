package com.nahmed.utils;

import com.nahmed.enums.ConfigProperties;

public final class ConfigurationManager {

    private ConfigurationManager() {
    }

    private static final String ENV_SYSTEM_PROPERTY = "env"; // System property: -Denv=CERT
    private static final String currentEnvironment;

    // Static initializer block: runs once and is thread-safe
    static {
        String envFromProps = PropertyUtils.getValue(ConfigProperties.ENVIRONMENT);
        String defaultEnvironment = (envFromProps == null || envFromProps.trim().isEmpty())
                ? "INT" : envFromProps;

        currentEnvironment = "_" + System.getProperty(ENV_SYSTEM_PROPERTY, defaultEnvironment);
    }

    public static String getCurrentEnvironment() {
        return currentEnvironment;
    }

}
