package com.nahmed.cucumber.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import com.nahmed.cucumber.enums.ConfigProperties;
import com.nahmed.cucumber.utils.PropertyUtils;

public class RetryFailedTests implements IRetryAnalyzer {

	private int count = 0;
	private int retries = 1;

	@Override
	public boolean retry(ITestResult result) {
		boolean value = false;
		if (PropertyUtils.getValue(ConfigProperties.RETRY_FAILED_TESTS).equalsIgnoreCase("yes")) {
			value = count < retries;
			count++;
		}
		return value;
	}

}
