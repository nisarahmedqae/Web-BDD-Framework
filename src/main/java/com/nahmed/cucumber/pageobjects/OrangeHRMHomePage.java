package com.nahmed.cucumber.pageobjects;

import com.nahmed.cucumber.enums.WaitStrategy;
import org.openqa.selenium.By;

public class OrangeHRMHomePage extends BasePage {

	private final By linkWelcome = By.xpath("//span[@class='oxd-userdropdown-tab']");
	private final By linkLogout = By.linkText("Logout");

	public OrangeHRMHomePage clickWelcome() {
		click(linkWelcome, WaitStrategy.PRESENCE, "welcome link");
		return this;
	}

	public OrangeHRMLoginPage clickLogout() {
		click(linkLogout, WaitStrategy.CLICKABLE, "Logout button");
		return new OrangeHRMLoginPage();
	}

}
