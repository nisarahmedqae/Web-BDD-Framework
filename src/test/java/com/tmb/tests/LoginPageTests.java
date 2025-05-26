package com.tmb.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

import com.tmb.driver.DriverManager;

public final class LoginPageTests extends BaseTest {
	private LoginPageTests() {

	}

	@Test
	public void test1() {
		DriverManager.getDriver().findElement(By.name("q")).sendKeys("Automation", Keys.ENTER);
	}


}
