package com.nahmed.cucumber.factories;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.nahmed.cucumber.constants.FrameworkConstants;
import com.nahmed.cucumber.driver.DriverManager;
import com.nahmed.cucumber.enums.WaitStrategy;

public final class ExplicitWaitFactory {

	private ExplicitWaitFactory() {

	}

	public static WebElement performExplicitWait(By by, WaitStrategy waitstrategy) {
		WebElement element = null;
		if (waitstrategy == WaitStrategy.CLICKABLE) {
			element = new WebDriverWait(DriverManager.getDriver(),
					Duration.ofSeconds(FrameworkConstants.getExplicitWait()))
					.until(ExpectedConditions.elementToBeClickable(by));
		} else if (waitstrategy == WaitStrategy.PRESENCE) {
			element = new WebDriverWait(DriverManager.getDriver(),
					Duration.ofSeconds(FrameworkConstants.getExplicitWait()))
					.until(ExpectedConditions.presenceOfElementLocated(by));
		} else if (waitstrategy == WaitStrategy.VISIBLE) {
			element = new WebDriverWait(DriverManager.getDriver(),
					Duration.ofSeconds(FrameworkConstants.getExplicitWait()))
					.until(ExpectedConditions.visibilityOfElementLocated(by));
		} else if (waitstrategy == WaitStrategy.NONE) {
			element = DriverManager.getDriver().findElement(by);
		}
		return element;
	}

}
