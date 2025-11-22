package com.nahmed.utils;

import com.nahmed.driver.DriverManager;
import com.nahmed.reports.ExtentLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.List;
import java.util.Set;

public class BrowserService {

    private TestContext testContext;
    private final WebDriver driver;
    private WaitHelper waitHelper;

    public BrowserService(TestContext testContext) {
        this.testContext = testContext;
        this.driver = DriverManager.getDriver();
    }

    public void openUrl(String url) {
        driver.get(url);
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public String getText(WebElement element) {
        String elementText = element.getText();
        try {
            ExtentLogger.pass("Element text is " + elementText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return elementText;
    }

    public void click(WebElement element, String elementName) {
        element.click();
        try {
            ExtentLogger.pass(elementName + " is clicked");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendKeys(WebElement element, String value, String elementName) {
        element.sendKeys(value);
        try {
            ExtentLogger.pass(value + " is entered successfully in " + elementName + " textbox");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearAndSendKeys(WebElement element, String value, String elementName) {
        element.clear();
        element.sendKeys(value);
        try {
            ExtentLogger.pass(value + " is entered successfully in " + elementName + " textbox");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getIndexOfElementWithText(List<WebElement> elements, String expectedText) {
        for (int i = 0; i < elements.size(); i++) {
            String actualText = elements.get(i).getText().trim();
            if (actualText.equalsIgnoreCase(expectedText.trim())) {
                ExtentLogger.pass("Matching text found in " + expectedText + ": '" + actualText + "' at index " + i);
                return i;
            }
        }

        ExtentLogger.fail("Element with text '" + expectedText + "' not found in " + expectedText);
        throw new RuntimeException("Element with text '" + expectedText + "' not found.");
    }

    public void clickElementAtIndex(By by, int index, String elementName) {
        List<WebElement> elements = driver.findElements(by);
        elements.get(index).click();
        try {
            ExtentLogger.pass(elementName + " is clicked");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void waitForPageLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(d -> ((JavascriptExecutor) d)
                        .executeScript("return document.readyState").equals("complete"));
    }

    public void switchToWindowByTitle(String pageTitle) {
        String originalWindow = driver.getWindowHandle();
        Set<String> allWindows = driver.getWindowHandles();

        for (String windowHandle : allWindows) {
            driver.switchTo().window(windowHandle);
            if (driver.getTitle().contains(pageTitle)) {
                System.out.println("Switched to window with title: " + pageTitle);
                return;
            }
        }

        // If no matching title is found, switch back to original
        driver.switchTo().window(originalWindow);
        System.out.println("No window with title '" + pageTitle + "' found. Switched back to original window.");
    }

    public void uploadFiles(WebElement inputLocator, List<String> fileNames) {
        StringBuilder filePaths = new StringBuilder();

        for (String fileName : fileNames) {
            String filePath = System.getProperty("user.dir")
                    + "/src/main/resources/attachments/"
                    + fileName;

            File file = new File(filePath);
            if (!file.exists()) {
                throw new RuntimeException("File not found: " + filePath);
            }

            filePaths.append(file.getAbsolutePath()).append("\n");
        }

        // Send all file paths (newline-separated for multiple files)
        inputLocator.sendKeys(filePaths.toString().trim());
    }

}
