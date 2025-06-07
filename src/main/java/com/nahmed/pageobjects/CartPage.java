package com.nahmed.pageobjects;

import com.nahmed.enums.WaitStrategy;
import com.nahmed.utils.BrowserService;
import com.nahmed.utils.DynamicXpathUtils;
import org.openqa.selenium.By;

public class CartPage {

    // Variables
    BrowserService browserService;

    // Constructor
    public CartPage() {
        this.browserService = new BrowserService();
    }

    // Locators
    private final By pageName = By.xpath("//p[text()='Automation Practice']");
    private final By myCart = By.xpath("//h1[text()='My Cart']");
    String productName = "//h3[text()='%s']";
    String productPrice = "//h3[text()='%s']/following-sibling::p[1]";

    // Methods
    public String getPageName() {
        return browserService.getText(pageName, WaitStrategy.VISIBLE, 15);
    }

    public String getMyCartText() {
        return browserService.getText(myCart, WaitStrategy.VISIBLE, 15);
    }

    public String getProductName(String expProductName) {
        String newXpath = DynamicXpathUtils.getXpath(productName, expProductName);
        return browserService.getText(By.xpath(newXpath), WaitStrategy.VISIBLE, 15);
    }

    public String getProductPrice(String expProductName) {
        String newXpath = DynamicXpathUtils.getXpath(productPrice, expProductName);
        return browserService.getText(By.xpath(newXpath), WaitStrategy.NONE, 0);
    }

}
