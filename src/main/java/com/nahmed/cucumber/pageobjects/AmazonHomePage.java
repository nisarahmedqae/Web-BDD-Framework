package com.nahmed.cucumber.pageobjects;

import com.nahmed.cucumber.driver.DriverManager;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AmazonHomePage extends BasePage {

	@FindBy(id = "nav-hamburger-menu")
	private WebElement linkHamBurger;

	public AmazonHomePage() {
		PageFactory.initElements(DriverManager.getDriver(), this);
	}

	public AmazonHamburgerMenuPage clickHamburger() {
		linkHamBurger.click();
		return new AmazonHamburgerMenuPage();
	}

}
