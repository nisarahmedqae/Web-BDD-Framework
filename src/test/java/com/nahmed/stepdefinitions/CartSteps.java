package com.nahmed.stepdefinitions;

import com.nahmed.pageobjects.CartPage;
import com.nahmed.pageobjects.HomePage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.testng.Assert;

public class CartSteps {

    HomePage homePage;
    CartPage cartPage;

    public CartSteps() {
        this.homePage = new HomePage();
        this.cartPage = new CartPage();
    }

    @And("the user navigates to the Cart page")
    public void theUserNavigatesToTheCartPage() {
        homePage.clickOnCartButton();
        Assert.assertEquals(cartPage.getMyCartText(), "My Cart");
    }

    @Then("the product {string} should be visible in the cart")
    public void theProductShouldBeVisibleInTheCart(String expProductName) {
        String actualProductName = cartPage.getProductName(expProductName);
        Assert.assertEquals(actualProductName, expProductName);
    }

    @And("the product {string} price should be {string}")
    public void theProductPriceShouldBe(String expProductName, String expProductPrice) {
        String actualProductPrice = cartPage.getProductPrice(expProductName);
        Assert.assertTrue(actualProductPrice.contains(expProductPrice));
    }
}

