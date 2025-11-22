package com.nahmed.pageobjects;

import com.nahmed.utils.BrowserService;
import com.nahmed.utils.TestContext;
import com.nahmed.utils.WaitHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class LoginPage {

    // Variables
    private TestContext testContext;
    private BrowserService browserService;
    private WaitHelper waitHelper;

    // Constructor
    public LoginPage(TestContext testContext) {
        this.testContext = testContext;
        this.browserService = new BrowserService(testContext);
        this.waitHelper = new WaitHelper();
    }

    // Locators
    private final By emailTextbox = By.id("userEmail");
    private final By passwordTextbox = By.id("userPassword");
    private final By loginButton = By.id("login");

    // Methods
    public LoginPage enterEmail(String email) {
        WebElement element = waitHelper.findElement(emailTextbox);
        browserService.sendKeys(element, email, "Email");
        return this;
    }

    public LoginPage enterPassword(String password) {
        WebElement element = waitHelper.findElement(passwordTextbox);
        browserService.sendKeys(element, password, "Password");
        return this;
    }

    public LoginPage clickOnLoginButton() {
        WebElement element = waitHelper.findElement(loginButton);
        browserService.click(element, "Login Button");
        return this;
    }

}
