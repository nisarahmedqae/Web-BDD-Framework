package com.nahmed.cucumber.utils;

import com.github.dzieciou.testing.curl.CurlRestAssuredConfigFactory;
import com.github.dzieciou.testing.curl.Options;
import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class TestContext {

    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(TestContext.class);

    public Response response;
    public Map<String, Object> session = new HashMap<>();
    private static final String CONTENT_TYPE = PropertiesFile.getProperty("content.type");

    // Constants for environment selection
    private static final String ENV_SYSTEM_PROPERTY = "env"; // System property: -Denv=CERT
    private static final String DEFAULT_ENV_PROPERTY_KEY = "default.env"; // Key in properties for default
    private static final String BASE_URL_PROPERTY_PREFIX = "baseURL."; // Prefix for base URLs

    // Store the determined environment for potential use elsewhere (like Hooks)
    private String currentEnvironment = null;

    public RequestSpecification requestSetup() {
        RestAssured.reset();

        // --- Environment Selection Logic ---
        if (currentEnvironment == null) {
            // 1. Get the default environment property value (might be null)
            String defaultEnvFromProps = PropertiesFile.getProperty(DEFAULT_ENV_PROPERTY_KEY);

            // 2. Assign "INT" if the property is null or empty, otherwise use the value from props
            String defaultEnvironment = (defaultEnvFromProps == null || defaultEnvFromProps.trim().isEmpty())
                    ? "INT"
                    : defaultEnvFromProps;

            // Read from system property, fall back to determined defaultEnvironment, convert to upper case
            this.currentEnvironment = System.getProperty(ENV_SYSTEM_PROPERTY, defaultEnvironment).toUpperCase();
            LOG.info("Determined target environment: " + this.currentEnvironment);
        }

        String baseUrlKey = BASE_URL_PROPERTY_PREFIX + this.currentEnvironment; // e.g., "baseURL.CERT"
        String baseUrlValue = PropertiesFile.getProperty(baseUrlKey);

        if (baseUrlValue == null || baseUrlValue.trim().isEmpty()) {
            String errorMessage = String.format(
                    "BaseURL not found in properties file for environment '%s' (Key: '%s'). Check config.properties.",
                    this.currentEnvironment, baseUrlKey);
            LOG.error(errorMessage);
            throw new IllegalStateException(errorMessage); // Fail fast
        }

        LOG.info(String.format("Setting BaseURL for environment [%s]: %s", this.currentEnvironment, baseUrlValue));
        RestAssured.baseURI = baseUrlValue; // Set the selected BaseURL
        // --- End Environment Selection Logic ---

        // --- RestAssured Configuration ---
        Options options = Options.builder().logStacktrace().build();
        RestAssuredConfig config = CurlRestAssuredConfigFactory.createConfig(options);

        // Build the request specification
        RequestSpecification request = RestAssured.given()
                .config(config)
                .filter(new RestAssuredRequestFilter())
                .contentType(CONTENT_TYPE)
                .accept(CONTENT_TYPE);

        // --- Add Authentication Header if Token Exists in Session ---
        // This assumes your Hooks class (or another step) puts the token here.
        // Check if the key from the OAuth hook exists
        if (session.containsKey("oauth_token")) {
            // If using Bearer token from OAuth hook
            String bearerToken = "Bearer " + session.get("oauth_token").toString();
            LOG.debug("Adding Authentication Bearer Header from session.");
            request = request.header("Authorization", bearerToken);
        }

        return request;
    }

    // Method to get the determined environment if needed elsewhere safely
    public String getCurrentEnvironment() {
        if (currentEnvironment == null) {
            String defaultEnvironment = PropertiesFile.getProperty(DEFAULT_ENV_PROPERTY_KEY, "INT");
            this.currentEnvironment = System.getProperty(ENV_SYSTEM_PROPERTY, defaultEnvironment).toUpperCase();
        }
        return currentEnvironment;
    }

}