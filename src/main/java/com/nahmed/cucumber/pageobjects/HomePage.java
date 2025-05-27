package com.nahmed.cucumber.pageobjects;

import com.nahmed.cucumber.enums.WaitStrategy;
import com.nahmed.cucumber.utils.BrowserService;
import org.openqa.selenium.By;

public class HomePage {

    // Variables
    BrowserService browserService;

    // Constructor
    public HomePage() {
        this.browserService = new BrowserService();
    }

    // Locators
    private final By pageName = By.xpath("//p[text()='Automation Practice']");

    // Methods

    public String getPageName() {
        return browserService.getText(pageName, WaitStrategy.VISIBLE);
    }

}
