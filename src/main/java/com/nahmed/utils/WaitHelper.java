package com.nahmed.utils;

import com.nahmed.driver.DriverManager;
import com.nahmed.enums.ConfigProperties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class WaitHelper {

    private WebDriver driver;
    private final int DEFAULT_EXPLICIT_WAIT = Integer.parseInt(PropertyUtils.getValue(ConfigProperties.DEFAULT_EXPLICIT_WAIT));

    public WaitHelper() {
        this.driver = DriverManager.getDriver();
    }

    public WebElement findElement(By locator) {
        return waitForPresence(locator, DEFAULT_EXPLICIT_WAIT);
    }

    // --- Wait for visibility of a single element (WebElement) ---
    public WebElement waitForVisibility(WebElement element, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    // --- OVERLOAD: Wait for visibility of a single element (By Locator) ---
    public WebElement waitForVisibility(By locator, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Wait for element to become invisible (WebElement)
    public boolean waitForInvisibility(WebElement element, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.invisibilityOf(element));
    }

    // Wait for element to become invisible (By Locator)
    public boolean waitForInvisibility(By locator, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    // --- Wait for clickability of a single element (WebElement) ---
    public WebElement waitForClickable(WebElement element, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    // --- OVERLOAD: Wait for clickability of a single element (By Locator) ---
    public WebElement waitForClickable(By locator, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    // --- Wait for presence using By Locator (Original method) ---
    public WebElement waitForPresence(By locator, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    // --- Wait for text in element (WebElement) ---
    public boolean waitForText(WebElement element, String text, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.textToBePresentInElement(element, text));
    }

    // --- OVERLOAD: Wait for text in element (By Locator) ---
    public boolean waitForText(By locator, String text, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    // --- Wait for visibility of all elements in a list (List<WebElement>) ---
    public List<WebElement> waitForVisibilityOfAll(List<WebElement> elements, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.visibilityOfAllElements(elements));
    }

    // --- OVERLOAD: Wait for visibility of all elements in a list (By Locator) ---
    public List<WebElement> waitForVisibilityOfAll(By locator, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }
}