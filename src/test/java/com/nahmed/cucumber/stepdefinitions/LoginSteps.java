package com.nahmed.cucumber.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import com.nahmed.cucumber.pageobjects.LoginPage; // Your existing Page Object
import com.yourcompany.pageobjects.DashboardPage; // Your existing Page Object
import com.yourcompany.utils.WebDriverManagerUtil; // Your ThreadLocal WebDriver manager (see below)

public class LoginSteps {

    private WebDriver driver;
    private LoginPage loginPage;
    private DashboardPage dashboardPage;

    // Using a Before hook (see Hooks.java) is better for driver setup
    // but for simplicity, you can get it here.
    // It's crucial that WebDriverManagerUtil.getDriver() returns a thread-safe driver.

    @Given("I am on the application login page")
    public void i_am_on_the_application_login_page() {
        driver = WebDriverManagerUtil.getDriver();
        loginPage = new LoginPage(driver); // Instantiate your Page Object
        driver.get("https://your-app-url.com/login"); // Navigate to login page
    }

    @When("I enter username {string} and password {string}")
    public void i_enter_username_and_password(String username, String password) {
        // Ensure loginPage is initialized if not done in a @Before hook or previous step
        if (loginPage == null) {
            driver = WebDriverManagerUtil.getDriver();
            loginPage = new LoginPage(driver);
        }
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }

    @And("I click on the login button")
    public void i_click_on_the_login_button() {
        loginPage.clickLoginButton();
    }

    @Then("I should be navigated to the dashboard")
    public void i_should_be_navigated_to_the_dashboard() {
        dashboardPage = new DashboardPage(driver);
        Assert.assertTrue(dashboardPage.isDashboardVisible(), "Not navigated to dashboard.");
    }

    @And("I should see a welcome message containing {string}")
    public void i_should_see_a_welcome_message_containing(String usernameSubstring) {
        String welcomeMessage = dashboardPage.getWelcomeMessage();
        Assert.assertTrue(welcomeMessage.contains(usernameSubstring),
                "Welcome message does not contain: " + usernameSubstring);
    }

    @Then("I should see an error message {string}")
    public void i_should_see_an_error_message(String expectedError) {
        Assert.assertEquals(loginPage.getErrorMessage(), expectedError, "Error message mismatch.");
    }
}

