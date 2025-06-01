package com.nahmed.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import com.nahmed.enums.ConfigProperties;
import com.nahmed.utils.PropertyUtils;

public class RetryFailedTests implements IRetryAnalyzer {

    private int count = 0;
    private int retries = Integer.parseInt(PropertyUtils.getValue(ConfigProperties.MAX_RETRIES_COUNT));

    @Override
    public boolean retry(ITestResult result) {
        boolean value = false;
        if (PropertyUtils.getValue(ConfigProperties.RETRY_FAILED_TESTS).equalsIgnoreCase("yes")) {
            System.out.println("nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn"+retries);
            value = count < retries;
            count++;
        }
        return value;
    }

}
