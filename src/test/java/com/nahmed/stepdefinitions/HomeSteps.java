package com.nahmed.stepdefinitions;

import com.nahmed.pageobjects.HomePage;
import com.nahmed.utils.AssertionService;
import io.cucumber.java.en.Then;

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

