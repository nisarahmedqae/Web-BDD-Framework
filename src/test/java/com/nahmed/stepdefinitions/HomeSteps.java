package com.nahmed.stepdefinitions;

import com.nahmed.listeners.TestListener;
import com.nahmed.pageobjects.HomePage;
import com.nahmed.utils.TestContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;

public class HomeSteps {

    private static final Logger LOG = LogManager.getLogger(TestListener.class);

    private TestContext testContext;
    private HomePage homePage;

    public HomeSteps(TestContext testContext) {
        this.testContext = testContext;
        this.homePage = new HomePage(testContext);
    }

    @Then("User should be navigated to the homepage {string}")
    public void userShouldBeNavigatedToTheHomepage(String expectedPageName) {
        String pageName = homePage.getPageName();
        Assert.assertEquals(pageName, expectedPageName, "Verify Page Name of Home Page");
    }

    @When("the user clicks on Add to Cart button for product {string}")
    public void theUserClicksOnAddToCartButtonForProduct(String productName) {
        homePage.clickOnAddToCartButtonForProduct(productName);
        LOG.info(homePage.getToastMessage());
    }
}

