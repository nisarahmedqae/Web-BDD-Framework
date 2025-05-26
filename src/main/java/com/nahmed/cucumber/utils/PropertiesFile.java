package com.nahmed.cucumber.utils; // Make sure package is correct

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFile {

    // Prevent instantiation of utility class
    private PropertiesFile() {
    }

    private static final Logger LOG = LoggerFactory.getLogger(PropertiesFile.class);
    private static Properties prop = new Properties();
    private static final String CONFIG_PATH = "src/main/resources/config.properties"; // Ensure path is correct

    // Static block to load properties when the class is loaded
    static {
        try (FileInputStream fis = new FileInputStream(CONFIG_PATH)) {
            prop.load(fis);
            LOG.info("Properties loaded successfully from: " + CONFIG_PATH);
        } catch (IOException e) {
            LOG.error("Error loading properties file: " + CONFIG_PATH, e);
        }
    }

    public static String getProperty(String key) {
        String value = prop.getProperty(key);
        if (value == null) {
            LOG.warn("Property key '" + key + "' not found in " + CONFIG_PATH);
        }
        return value;
    }

    public static String getProperty(String key, String defaultValue) {
        String value = prop.getProperty(key);
        // Return defaultValue if the property is null or an empty string
        return (value == null || value.trim().isEmpty()) ? defaultValue : value;
    }

}