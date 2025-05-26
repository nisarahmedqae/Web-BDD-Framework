package com.tmb.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.tmb.driver.DriverManager;
import com.tmb.enums.WaitStrategy;
import com.tmb.factories.ExplicitWaitFactory;
import com.tmb.reports.ExtentLogger;

public class BasePage {

	// Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(5));

	protected void click(By by, WaitStrategy waitstrategy, String elementname) {
		WebElement element = ExplicitWaitFactory.performExplicitWait(waitstrategy, by);
		element.click();
		try {
			ExtentLogger.pass(elementname + " is clicked", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void sendKeys(By by, String value, WaitStrategy waitstrategy, String elementname) {
		WebElement element = ExplicitWaitFactory.performExplicitWait(waitstrategy, by);
		element.sendKeys(value);
		try {
			ExtentLogger.pass(value + " is entered successfully in " + elementname + " textbox", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected String getTitle() {
		return DriverManager.getDriver().getTitle();
	}

}
