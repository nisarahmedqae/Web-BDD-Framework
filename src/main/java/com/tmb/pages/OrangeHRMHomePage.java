package com.tmb.pages;

import org.openqa.selenium.By;

import com.tmb.enums.WaitStrategy;

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
