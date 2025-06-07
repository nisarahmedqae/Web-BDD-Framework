package com.nahmed.pageobjects;

import com.nahmed.enums.WaitStrategy;
import com.nahmed.factories.ExplicitWaitFactory;
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
    private final By cartButton = By.xpath("//button[@routerlink='/dashboard/cart']");
    private final By pageName = By.xpath("//p[text()='Automation Practice']");
    private final By products = By.xpath("//div[@class='card-body']//b");
    private final By addToCartButton = By.xpath("//div[@class='card-body']//button[@class='btn w-10 rounded']");
    private final By toastMessage = By.xpath("//div[@id='toast-container']/div");

    // Methods
    public String getToastMessage() {
        return browserService.getText(toastMessage, WaitStrategy.VISIBLE, 10);
    }

    public String getPageName() {
        return browserService.getText(pageName, WaitStrategy.VISIBLE, 15);
    }

    public void clickOnAddToCartButtonForProduct(String productName) {
        boolean isGone = ExplicitWaitFactory.waitUntilElementIsInvisible(toastMessage, 10);
        if (isGone) {
            int index = browserService.getIndexOfElementWithText(products, productName, WaitStrategy.VISIBLE, 10, "Products");
            browserService.clickElementAtIndex(addToCartButton, index, "Add to Cart Button");
        }
    }

    public void clickOnCartButton() {
        browserService.click(cartButton, WaitStrategy.VISIBLE, 15, "Cart Button");
    }

}
