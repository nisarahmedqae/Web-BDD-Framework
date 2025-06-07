package com.nahmed.pageobjects;

import com.nahmed.enums.WaitStrategy;
import com.nahmed.utils.BrowserService;
import org.openqa.selenium.By;

public class LoginPage {

    // Variables
    BrowserService browserService;

    // Constructor
    public LoginPage() {
        this.browserService = new BrowserService();
    }

    // Locators
    private final By emailTextbox = By.id("userEmail");
    private final By passwordTextbox = By.id("userPassword");
    private final By loginButton = By.id("login");

    // Methods
    public LoginPage enterEmail(String email) {
        browserService.sendKeys(emailTextbox, email, WaitStrategy.NONE, 0, "Email");
        return this;
    }

    public LoginPage enterPassword(String password) {
        browserService.sendKeys(passwordTextbox, password, WaitStrategy.NONE, 0, "Password");
        return this;
    }

    public LoginPage clickOnLoginButton() {
        browserService.click(loginButton, WaitStrategy.NONE, 0, "Login Button");
        return this;
    }

}
