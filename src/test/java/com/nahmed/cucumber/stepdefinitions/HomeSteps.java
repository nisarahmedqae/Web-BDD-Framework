package com.nahmed.cucumber.stepdefinitions;

import com.nahmed.cucumber.enums.ConfigProperties;
import com.nahmed.cucumber.pageobjects.HomePage;
import com.nahmed.cucumber.pageobjects.LoginPage;
import com.nahmed.cucumber.utils.AssertionService;
import com.nahmed.cucumber.utils.BrowserService;
import com.nahmed.cucumber.utils.PropertyUtils;
import com.nahmed.cucumber.utils.TestContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class HomeSteps {

    HomePage homePage;
    AssertionService assertionService;

    public HomeSteps() {
        this.homePage = new HomePage();
        this.assertionService = new AssertionService();
    }

    @Then("User should be navigated to the homepage {string}")
    public void userShouldBeNavigatedToTheHomepage(String expectedPageName) {
        String pageName = homePage.getPageName();
        assertionService.assertEquals(pageName, expectedPageName, "Verify Page Name of Home Page");
    }
}

