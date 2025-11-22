package com.nahmed.pageobjects;

import com.nahmed.utils.BrowserService;
import com.nahmed.utils.TestContext;
import com.nahmed.utils.WaitHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class HomePage {

    // Variables
    private TestContext testContext;
    private BrowserService browserService;
    private WaitHelper waitHelper;

    // Constructor
    public HomePage(TestContext testContext) {
        this.testContext = testContext;
        this.browserService = new BrowserService(testContext);
        this.waitHelper = new WaitHelper();

    }

    // Locators
    private final By cartButton = By.xpath("//button[@routerlink='/dashboard/cart']");
    private final By pageName = By.xpath("//p[text()='Automation Practice']");
    private final By products = By.xpath("//div[@class='card-body']//b");
    private final By addToCartButton = By.xpath("//div[@class='card-body']//button[@class='btn w-10 rounded']");
    private final By toastMessage = By.xpath("//div[@id='toast-container']/div");

    // Methods
    public String getToastMessage() {
        WebElement element = waitHelper.waitForVisibility(toastMessage, 10);
        return browserService.getText(element);
    }

    public String getPageName() {
        WebElement element = waitHelper.waitForVisibility(pageName, 15);
        return browserService.getText(element);
    }

    public void clickOnAddToCartButtonForProduct(String productName) {
        boolean isGone = waitHelper.waitForInvisibility(toastMessage, 10);

        if (isGone) {
            List<WebElement> products = waitHelper.waitForVisibilityOfAll(this.products, 10);
            int index = browserService.getIndexOfElementWithText(products, productName);
            browserService.clickElementAtIndex(addToCartButton, index, "Add to Cart Button");
        }
    }

    public void clickOnCartButton() {
        WebElement element = waitHelper.waitForVisibility(cartButton, 15);
        browserService.click(element, "Cart Button");
    }

}
