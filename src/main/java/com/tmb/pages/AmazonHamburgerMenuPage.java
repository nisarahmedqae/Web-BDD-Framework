package com.tmb.pages;

import org.openqa.selenium.By;

import com.tmb.enums.WaitStrategy;
import com.tmb.utils.DynamicXpathUtils;

public class AmazonHamburgerMenuPage extends BasePage {

	private String linkComputers = "//div[text()='Mobiles, Computers']/..";
	private String linkSubMenu = "//a[text()='%s']";

	public AmazonHamburgerMenuPage clickComputer() {
		click(By.xpath(linkComputers), WaitStrategy.CLICKABLE, "Mobiles, Computers");
		return this;
	}

	public AmazonLaptopPage clickOnSubMenuItem(String menutext) {
		String newxpath = DynamicXpathUtils.getXpath(linkSubMenu, menutext);
		click(By.xpath(newxpath), WaitStrategy.CLICKABLE, menutext);
		if (menutext.contains("Laptops")) {
			return new AmazonLaptopPage();
		}
		return null;
	}

}
