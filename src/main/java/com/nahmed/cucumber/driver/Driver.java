package com.nahmed.cucumber.driver;

import java.time.Duration;
import java.util.Objects;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;

import com.nahmed.cucumber.enums.ConfigProperties;
import com.nahmed.cucumber.utils.PropertyUtils;

public final class Driver {

    private Driver() {

    }

    public static void initDriver(String browser) {
        if (Objects.isNull(DriverManager.getDriver())) {

            if (browser.contains("chrome")) {
                ChromeOptions options = new ChromeOptions();
                if (browser.contains("headless")) {
                    options.addArguments("headless");
                }
                WebDriver driver = new ChromeDriver(options);
                DriverManager.setDriver(driver);

            } else if (browser.contains("edge")) {
                WebDriver driver = new EdgeDriver();
                DriverManager.setDriver(driver);
            }

            DriverManager.getDriver().manage().window().setSize(new Dimension(1440, 900));
            int seconds = Integer.parseInt(PropertyUtils.getValue(ConfigProperties.IMPLICIT_WAIT));
            DriverManager.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(seconds));
            DriverManager.getDriver().manage().window().maximize();

        }
    }

    public static void quitDriver() {
        if (Objects.nonNull(DriverManager.getDriver())) {
            DriverManager.getDriver().quit();
            DriverManager.unload();
        }
    }

}
