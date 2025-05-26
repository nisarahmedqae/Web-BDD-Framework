package com.nahmed.cucumber.driver;

import java.util.Objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import com.nahmed.cucumber.enums.ConfigProperties;
import com.nahmed.cucumber.utils.PropertyUtils;

public final class Driver {

	private Driver() {

	}

	public static void initDriver(String browser) {
		if (Objects.isNull(DriverManager.getDriver())) {

			if (browser.equalsIgnoreCase("chrome")) {
				WebDriver driver = new ChromeDriver();
				DriverManager.setDriver(driver);
			} else if (browser.equalsIgnoreCase("edge")) {
				WebDriver driver = new EdgeDriver();
				DriverManager.setDriver(driver);
			}

			DriverManager.getDriver().get(PropertyUtils.getValue(ConfigProperties.URL));
		}
	}

	public static void quitDriver() {
		if (Objects.nonNull(DriverManager.getDriver())) {
			DriverManager.getDriver().quit();
			DriverManager.unload();
		}
	}

}
