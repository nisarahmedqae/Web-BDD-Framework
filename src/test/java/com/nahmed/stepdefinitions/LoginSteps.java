package com.nahmed.stepdefinitions;

import com.nahmed.enums.ConfigProperties;
import com.nahmed.pageobjects.HomePage;
import com.nahmed.pageobjects.LoginPage;
import com.nahmed.utils.AssertionService;
import com.nahmed.utils.BrowserService;
import com.nahmed.utils.PropertyUtils;
import com.nahmed.utils.TestContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class LoginSteps {

    LoginPage loginPage;
    HomePage homePage;
    TestContext testContext;
    BrowserService browserService;
    AssertionService assertionService;

    public LoginSteps() {
        this.loginPage = new LoginPage();
        this.testContext = new TestContext();
        this.browserService = new BrowserService();
        this.homePage = new HomePage();
        this.assertionService = new AssertionService();
    }

    @Given("User is on the application login page")
    public void userIsOnTheApplicationLoginPage() {
        String url = PropertyUtils.getValue(ConfigProperties.URL + testContext.getCurrentEnvironment());
        browserService.openUrl(url);
    }

    @When("User enters email {string} and password {string}")
    public void userEntersEmailAndPassword(String email, String password) {
        loginPage.enterEmail(email).enterPassword(password);
    }

    @And("User click on the login button")
    public void userClickOnTheLoginButton() {
        loginPage.clickOnLoginButton();
    }

}

