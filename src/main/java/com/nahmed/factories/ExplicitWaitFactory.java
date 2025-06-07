package com.nahmed.factories;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.nahmed.driver.DriverManager;
import com.nahmed.enums.WaitStrategy;

public final class ExplicitWaitFactory {

	private ExplicitWaitFactory() {

	}

	public static WebElement performExplicitWaitForElement(By by, WaitStrategy waitstrategy, int waitTimeInSeconds) {
		WebElement element = null;

		WebDriverWait wait = new WebDriverWait(
				DriverManager.getDriver(),
				Duration.ofSeconds(waitTimeInSeconds)
		);

		if (waitstrategy == WaitStrategy.CLICKABLE) {
			element = wait.until(ExpectedConditions.elementToBeClickable(by));
		} else if (waitstrategy == WaitStrategy.PRESENCE) {
			element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
		} else if (waitstrategy == WaitStrategy.VISIBLE) {
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		} else if (waitstrategy == WaitStrategy.NONE) {
			element = DriverManager.getDriver().findElement(by);
		}
		return element;
	}

	public static List<WebElement> performExplicitWaitForElementList(By by, WaitStrategy waitStrategy, int waitTimeInSeconds) {
		List<WebElement> elements = null;

		WebDriverWait wait = new WebDriverWait(
				DriverManager.getDriver(),
				Duration.ofSeconds(waitTimeInSeconds)
		);

		if (waitStrategy == WaitStrategy.VISIBLE) {
			elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
		} else if (waitStrategy == WaitStrategy.PRESENCE) {
			elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
		} else if (waitStrategy == WaitStrategy.NONE) {
			elements = DriverManager.getDriver().findElements(by);
		}

		return elements;
	}

	public static Boolean waitUntilElementIsInvisible(By by, int waitTimeInSeconds) {
		try {
			return new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(waitTimeInSeconds))
					.until(ExpectedConditions.invisibilityOfElementLocated(by));
		} catch (Exception e) {
			return false;
		}
	}


}
