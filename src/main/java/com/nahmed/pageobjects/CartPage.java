package com.nahmed.pageobjects;

import com.nahmed.driver.DriverManager;
import com.nahmed.utils.BrowserService;
import com.nahmed.utils.DynamicXpathUtils;
import com.nahmed.utils.TestContext;
import com.nahmed.utils.WaitHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CartPage {

    // Variables
    private TestContext testContext;
    private BrowserService browserService;
    private WebDriver driver;
    private WaitHelper waitHelper;

    // Constructor
    public CartPage(TestContext testContext) {
        this.testContext = testContext;
        this.browserService = new BrowserService(testContext);
        this.waitHelper = new WaitHelper();
        this.driver = DriverManager.getDriver();
    }

    // Locators
    private final By pageName = By.xpath("//p[text()='Automation Practice']");
    private final By myCart = By.xpath("//h1[text()='My Cart']");
    String productName = "//h3[text()='%s']";
    String productPrice = "//h3[text()='%s']/following-sibling::p[1]";

    // Methods
    public String getPageName() {
        WebElement element = waitHelper.waitForVisibility(pageName, 15);
        return browserService.getText(element);
    }

    public String getMyCartText() {
        WebElement element = waitHelper.waitForVisibility(myCart, 15);
        return browserService.getText(element);
    }

    public String getProductName(String expProductName) {
        String newXpath = DynamicXpathUtils.getXpath(productName, expProductName);
        WebElement element = waitHelper.waitForVisibility(By.xpath(newXpath), 15);
        return browserService.getText(element);
    }

    public String getProductPrice(String expProductName) {
        String newXpath = DynamicXpathUtils.getXpath(productPrice, expProductName);
        WebElement element = driver.findElement(By.xpath(newXpath));
        return browserService.getText(element);
    }

}
