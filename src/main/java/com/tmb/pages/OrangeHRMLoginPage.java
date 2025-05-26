package com.tmb.pages;

import org.openqa.selenium.By;

import com.tmb.enums.WaitStrategy;
import com.tmb.utils.DecodeUtils;

public final class OrangeHRMLoginPage extends BasePage {
	// All your page methods needs to have some return type

	private final By textboxUsername = By.xpath("//input[@placeholder='Username']");
	private final By textboxPassword = By.xpath("//input[@placeholder='Password']");
	private final By buttonLogin = By.xpath("//button[@type='submit']");

	public OrangeHRMLoginPage enterUserName(String username) {
		sendKeys(textboxUsername, username, WaitStrategy.PRESENCE, "Username");
		return this;
	}

	public OrangeHRMLoginPage enterPassword(String password) {
		sendKeys(textboxPassword, DecodeUtils.getDecodedString(password), WaitStrategy.CLICKABLE, "Password");
		return this;
	}

	public OrangeHRMHomePage clickLogin() {
		click(buttonLogin, WaitStrategy.CLICKABLE, "Login Button");
		return new OrangeHRMHomePage();
	}

	public String getPageTitle() {
		return getTitle();
	}

}
