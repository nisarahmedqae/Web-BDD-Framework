package com.nahmed.tests;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.nahmed.driver.DriverManager;

public final class HomePageTests extends BaseTest {
	private HomePageTests() {

	}
	
	/*
	 * validate whether title containing "google search"
	 * validate whether title is not null and the length of title greater than 15 and less than 100
	 * check for the links in the pages --> testing mini byte - youtube
	 * number of links displayed is exactly 10 or 15
	 */

	@Test
	public void test2() throws InterruptedException {
		DriverManager.getDriver().findElement(By.name("q")).sendKeys("Testing Mini Bytes YouTube", Keys.ENTER);
		String title = DriverManager.getDriver().getTitle();
		System.out.println(title);
		System.out.println(title.length());
		
		assertThat(title)
		.as("Object is actually null").isNotNull()
		.as("It does not contain expected text").containsIgnoringCase("google search")
		.hasSizeBetween(15, 100);
		
		List<WebElement> elements = DriverManager.getDriver().findElements(By.xpath("//div[contains(@class,'notranslate')]//span[@class='VuuXrf']"));
		assertThat(elements)
		.hasSizeGreaterThan(10)
		.extracting(WebElement :: getText)
		.contains("Testing Mini Bytes");
	}

}
