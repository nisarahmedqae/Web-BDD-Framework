package com.nahmed.stepdefinitions;

import com.nahmed.enums.ConfigProperties;
import com.nahmed.listeners.TestListener;
import com.nahmed.pageobjects.HomePage;
import com.nahmed.pageobjects.LoginPage;
import com.nahmed.utils.BrowserService;
import com.nahmed.utils.PropertyUtils;
import com.nahmed.utils.TestContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class LoginSteps {

    private static final Logger LOG = LogManager.getLogger(TestListener.class);

    LoginPage loginPage;
    HomePage homePage;
    TestContext testContext;
    BrowserService browserService;

    public LoginSteps() {
        this.loginPage = new LoginPage();
        this.testContext = new TestContext();
        this.browserService = new BrowserService();
        this.homePage = new HomePage();
    }

    @Given("User is on the application login page")
    public void userIsOnTheApplicationLoginPage() {
        String url = PropertyUtils.getValue(ConfigProperties.URL + testContext.getCurrentEnvironment());
        System.out.println(url);
        System.out.println(ConfigProperties.URL + testContext.getCurrentEnvironment());
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

    @Given("the user is logged into the application")
    public void theUserIsLoggedIntoTheApplication() {
        String url = PropertyUtils.getValue(ConfigProperties.URL + testContext.getCurrentEnvironment());
        browserService.openUrl(url);
        loginPage.enterEmail(PropertyUtils.getValue(ConfigProperties.USERNAME + testContext.getCurrentEnvironment()))
                .enterPassword(PropertyUtils.getValue(ConfigProperties.PASSWORD + testContext.getCurrentEnvironment()));
        loginPage.clickOnLoginButton();
        LOG.info(homePage.getToastMessage());
    }
}

