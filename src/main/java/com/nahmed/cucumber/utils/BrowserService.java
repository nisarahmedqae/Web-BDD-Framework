package com.nahmed.cucumber.utils;

import com.nahmed.cucumber.driver.DriverManager;
import com.nahmed.cucumber.enums.WaitStrategy;
import com.nahmed.cucumber.factories.ExplicitWaitFactory;
import com.nahmed.cucumber.reports.ExtentLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class BrowserService {

    public void openUrl(String url) {
        DriverManager.getDriver().get(url);
    }

    public String getTitle() {
        return DriverManager.getDriver().getTitle();
    }

    public String getText(By by, WaitStrategy waitstrategy) {
        WebElement element = ExplicitWaitFactory.performExplicitWait(by, waitstrategy);
        String elementText = element.getText();
        try {
            ExtentLogger.pass("Element text is " + elementText, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return elementText;
    }

    public void click(By by, WaitStrategy waitstrategy, String elementname) {
        WebElement element = ExplicitWaitFactory.performExplicitWait(by, waitstrategy);
        element.click();
        try {
            ExtentLogger.pass(elementname + " is clicked", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendKeys(By by, String value, WaitStrategy waitstrategy, String elementname) {
        WebElement element = ExplicitWaitFactory.performExplicitWait(by, waitstrategy);
        element.sendKeys(value);
        try {
            ExtentLogger.pass(value + " is entered successfully in " + elementname + " textbox", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearAndSendKeys(By by, String value, WaitStrategy waitstrategy, String elementname) {
        WebElement element = ExplicitWaitFactory.performExplicitWait(by, waitstrategy);
        element.clear();
        element.sendKeys(value);
        try {
            ExtentLogger.pass(value + " is entered successfully in " + elementname + " textbox", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
