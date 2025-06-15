package com.nahmed.utils;

import com.nahmed.driver.DriverManager;
import com.nahmed.enums.WaitStrategy;
import com.nahmed.factories.ExplicitWaitFactory;
import com.nahmed.reports.ExtentLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BrowserService {

    public void openUrl(String url) {
        DriverManager.getDriver().get(url);
    }

    public String getTitle() {
        return DriverManager.getDriver().getTitle();
    }

    public String getText(By by, WaitStrategy waitstrategy, int waitTimeInSeconds) {
        WebElement element = ExplicitWaitFactory.performExplicitWaitForElement(by, waitstrategy, waitTimeInSeconds);
        String elementText = element.getText();
        try {
            ExtentLogger.pass("Element text is " + elementText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return elementText;
    }

    public void click(By by, WaitStrategy waitstrategy, int waitTimeInSeconds, String elementName) {
        WebElement element = ExplicitWaitFactory.performExplicitWaitForElement(by, waitstrategy, waitTimeInSeconds);
        element.click();
        try {
            ExtentLogger.pass(elementName + " is clicked");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendKeys(By by, String value, WaitStrategy waitstrategy, int waitTimeInSeconds, String elementName) {
        WebElement element = ExplicitWaitFactory.performExplicitWaitForElement(by, waitstrategy, waitTimeInSeconds);
        element.sendKeys(value);
        try {
            ExtentLogger.pass(value + " is entered successfully in " + elementName + " textbox");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearAndSendKeys(By by, String value, WaitStrategy waitstrategy, int waitTimeInSeconds, String elementName) {
        WebElement element = ExplicitWaitFactory.performExplicitWaitForElement(by, waitstrategy, waitTimeInSeconds);
        element.clear();
        element.sendKeys(value);
        try {
            ExtentLogger.pass(value + " is entered successfully in " + elementName + " textbox");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getIndexOfElementWithText(By by, String expectedText, WaitStrategy waitStrategy, int waitTimeInSeconds, String elementName) {
        List<WebElement> elements = ExplicitWaitFactory.performExplicitWaitForElementList(by, waitStrategy, waitTimeInSeconds);

        for (int i = 0; i < elements.size(); i++) {
            String actualText = elements.get(i).getText().trim();
            if (actualText.equalsIgnoreCase(expectedText.trim())) {
                ExtentLogger.pass("Matching text found in " + elementName + ": '" + actualText + "' at index " + i);
                return i;
            }
        }

        ExtentLogger.fail("Element with text '" + expectedText + "' not found in " + elementName);
        throw new RuntimeException("Element with text '" + expectedText + "' not found.");
    }

    public void clickElementAtIndex(By by, int index, String elementName) {
        List<WebElement> elements = DriverManager.getDriver().findElements(by);
        elements.get(index).click();
        try {
            ExtentLogger.pass(elementName + " is clicked");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickAfterInvisibility(By invisibleElement, int invisibleElementWait, By clickableElement, WaitStrategy waitStrategy, int waitTimeInSeconds, String elementName) {
        boolean isGone = ExplicitWaitFactory.waitUntilElementIsInvisible(invisibleElement, invisibleElementWait);
        if (isGone) {
            click(clickableElement, waitStrategy, waitTimeInSeconds, elementName);
        } else {
            ExtentLogger.fail("Element '" + invisibleElement + "' did not disappear. Skipping click on " + elementName);
            throw new RuntimeException("Element not invisible, cannot click: " + elementName);
        }
    }

    public void waitForPageLoad() {
        new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(30))
                .until(d -> ((JavascriptExecutor) d)
                        .executeScript("return document.readyState").equals("complete"));
    }


}
