package com.nahmed.pageobjects;

import com.nahmed.enums.WaitStrategy;
import com.nahmed.utils.BrowserService;
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
