package com.nahmed.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import com.nahmed.enums.ConfigProperties;
import com.nahmed.utils.PropertyUtils;
import org.apache.log4j.LogManager; // Assuming you use Log4j
import org.apache.log4j.Logger;    // Assuming you use Log4j

public class RetryFailedTests implements IRetryAnalyzer {

	private static final Logger LOG = LogManager.getLogger(RetryFailedTests.class);
	private int count = 0;
	private static final int MAX_RETRIES; // Made static and final

	static {
		int configuredRetries = 1; // Default value
		try {
			String retryValue = PropertyUtils.getValue(ConfigProperties.MAX_RETRIES_COUNT); // New config property
			if (retryValue != null && !retryValue.trim().isEmpty()) {
				configuredRetries = Integer.parseInt(retryValue);
			}
		} catch (NumberFormatException e) {
			LOG.warn("Could not parse MAX_RETRIES_COUNT from properties. Using default: " + configuredRetries, e);
		} catch (Exception e) { // Catching general exception if PropertyUtils.getValue throws something else
			LOG.warn("Error reading MAX_RETRIES_COUNT from properties. Using default: " + configuredRetries, e);
		}
		MAX_RETRIES = configuredRetries;
	}

	@Override
	public boolean retry(ITestResult result) {
		boolean shouldRetry = false;
		if (PropertyUtils.getValue(ConfigProperties.RETRY_FAILED_TESTS).equalsIgnoreCase("yes")) {
			if (count < MAX_RETRIES) {
				count++;
				LOG.info("Retrying test: " + result.getName() + " for the " + count + " time(s) out of " + MAX_RETRIES + ".");
				shouldRetry = true;
			} else {
				LOG.info("Not retrying test: " + result.getName() + ". Max retries (" + MAX_RETRIES + ") reached.");
			}
		}
		return shouldRetry;
	}
}
    