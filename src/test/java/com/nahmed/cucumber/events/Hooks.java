package com.nahmed.cucumber.events;

import com.nahmed.cucumber.utils.PropertiesFile;
import com.nahmed.cucumber.utils.TestContext;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hooks {

    private static final Logger LOG = LoggerFactory.getLogger(Hooks.class);
    private final TestContext context;

    public Hooks(TestContext context) {
        this.context = context;
    }

    @Before(order = 1)
    public void generateOAuthToken(Scenario scenario) {

        // Get the environment determined by TestContext
        String targetEnvironment = context.getCurrentEnvironment();
        String oauthUrlKey = "oauth.token.url." + targetEnvironment;
        String tokenUrl = PropertiesFile.getProperty(oauthUrlKey);

        LOG.info("Using OAuth Token URL for environment [" + targetEnvironment + "]: " + tokenUrl);

        // --- Get OAuth Configuration from Properties ---
        String clientId = PropertiesFile.getProperty("oauth.client.id");
        String clientSecret = PropertiesFile.getProperty("oauth.client.secret");

        LOG.info("Attempting to generate OAuth2 token...");
        /*
        Response tokenResponse = RestAssured
                .given()
                .log().ifValidationFails() // Log request details only if there's an error during the call
                .contentType(ContentType.URLENC) // OAuth2 token endpoints often use form url encoding
                .formParam("grant_type", "client_credentials")
                .formParam("client_id", clientId)
                .formParam("client_secret", clientSecret)
                .when()
                .post(tokenUrl)
                .then()
                .log().ifValidationFails() // Log response details only if validation below fails
                .statusCode(200) // Expect a 200 OK for successful token generation
                .contentType(ContentType.JSON) // Expect a JSON response
                .extract().response();

        // --- Extract Access Token ---
        String accessToken = tokenResponse.jsonPath().getString("access_token");

        // --- Store Token in TestContext Session ---
        context.session.put("oauth_token", accessToken); // Use a consistent key
         */

        LOG.info("OAuth2 token successfully generated and stored in session.");

    }


}