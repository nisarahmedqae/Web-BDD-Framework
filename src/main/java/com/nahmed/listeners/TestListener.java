package com.nahmed.listeners;

import com.nahmed.reports.ExtentLogger;
import com.nahmed.reports.ExtentReport;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class TestListener implements ConcurrentEventListener {

    private static final Logger LOG = LogManager.getLogger(TestListener.class);

    // ANSI Escape Codes for colors
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_PURPLE = "\u001B[35m";

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        // Suite-level events
        publisher.registerHandlerFor(TestRunStarted.class, this::handleTestRunStarted);
        publisher.registerHandlerFor(TestRunFinished.class, this::handleTestRunFinished);

        // Scenario-level events
        publisher.registerHandlerFor(TestCaseStarted.class, this::handleTestCaseStarted);
        publisher.registerHandlerFor(TestCaseFinished.class, this::handleTestCaseFinished);

        // Step-level events
        publisher.registerHandlerFor(TestStepStarted.class, this::handleTestStepStarted);
        publisher.registerHandlerFor(TestStepFinished.class, this::handleTestStepFinished);
    }

    // --- Handler for Test Run Started (Suite Start) ---
    private void handleTestRunStarted(TestRunStarted event) {
        LOG.info(ANSI_PURPLE + "=========================================================================================" + ANSI_RESET);
        LOG.info(ANSI_PURPLE + " CUCUMBER TEST EXECUTION STARTED " + ANSI_RESET);
        LOG.info(ANSI_PURPLE + " Timestamp: " + event.getInstant() + ANSI_RESET);
        LOG.info(ANSI_PURPLE + "=========================================================================================" + ANSI_RESET);

        ExtentReport.initReports();
    }


    // --- Handler for Scenario Started ---
    private void handleTestCaseStarted(TestCaseStarted event) {
        TestCase testCase = event.getTestCase();
        String testCaseName = testCase.getName();
        String featureName = testCase.getUri().toString().substring(testCase.getUri().toString().lastIndexOf('/') + 1);
        ExtentReport.createTest(featureName + " : " + testCaseName);

        LOG.info(ANSI_BLUE + "*****************************************************************************************" + ANSI_RESET);
        LOG.info(ANSI_BLUE + "Feature: " + featureName + ANSI_RESET);
        LOG.info(ANSI_BLUE + "Starting Scenario: " + testCaseName + " (Line: " + testCase.getLine() + ")" + ANSI_RESET);
        LOG.info(ANSI_BLUE + "Tags: " + String.join(", ", testCase.getTags()) + ANSI_RESET);
        LOG.info(ANSI_BLUE + "*****************************************************************************************" + ANSI_RESET);
    }


    // --- Handler for Step Started ---
    private void handleTestStepStarted(TestStepStarted event) {
        TestStep testStep = event.getTestStep();
        if (testStep instanceof PickleStepTestStep) {
            PickleStepTestStep pickleStep = (PickleStepTestStep) testStep;
            String stepText = pickleStep.getStep().getText();
            String keyword = pickleStep.getStep().getKeyword(); // e.g., "Given ", "When ", "Then "
            String stepDescription = keyword.trim() + " " + stepText;
            LOG.info(ANSI_BLUE + "  " + stepDescription + ANSI_RESET);

            ExtentReport.addStep(stepDescription);
        }
    }

    // --- Handler for Step Finished ---
    private void handleTestStepFinished(TestStepFinished event) {
        TestStep testStep = event.getTestStep();
        Result result = event.getResult();
        Status status = result.getStatus();
        Throwable error = result.getError();

        if (testStep instanceof PickleStepTestStep) {
            PickleStepTestStep pickleStep = (PickleStepTestStep) testStep;
            String stepText = pickleStep.getStep().getText();
            String keyword = pickleStep.getStep().getKeyword();
            String stepDescription = keyword.trim() + " " + stepText;

            String color = ANSI_RESET;
            String statusPrefix = "  STEP ";

            switch (status) {
                case PASSED:
                    color = ANSI_GREEN;
                    ExtentLogger.pass(stepDescription + " is PASSED");
                    LOG.info(color + statusPrefix + status + ": " + stepDescription + ANSI_RESET);
                    break;
                case FAILED:
                    color = ANSI_RED;
                    ExtentLogger.fail(stepDescription + " is FAILED", true); // true for screenshot
                    if (error != null) {
                        ExtentLogger.fail("Step Error Details: " + error.toString());
                        LOG.error(color + "  Underlying Step Failure Cause: " + ANSI_RESET, error);
                    }
                    LOG.error(color + statusPrefix + status + ": " + stepDescription + ANSI_RESET); // Log the failing step description
                    break;
                case SKIPPED:
                    color = ANSI_YELLOW;
                    ExtentLogger.skip(stepDescription + " is SKIPPED");
                    if (error != null) {
                        LOG.info(color + "  Reason for Skip: " + error.getMessage() + ANSI_RESET);
                    }
                    LOG.info(color + statusPrefix + status + ": " + stepDescription + ANSI_RESET);
                    break;
                case PENDING:
                    color = ANSI_YELLOW;
                    ExtentLogger.skip(stepDescription + " is PENDING");
                    LOG.info(color + statusPrefix + status + ": " + stepDescription + ANSI_RESET);
                    break;
                case UNDEFINED:
                    color = ANSI_YELLOW;
                    ExtentLogger.skip(stepDescription + " is UNDEFINED (step definition missing)");
                    LOG.info(color + statusPrefix + status + ": " + stepDescription + ANSI_RESET);
                    break;
                case AMBIGUOUS:
                    color = ANSI_RED; // Treat ambiguous as a failure for console color
                    ExtentLogger.fail(stepDescription + " is AMBIGUOUS (multiple step definitions found)", true);
                    if (result.getError() != null) { // Use result.getError() for ambiguous
                        ExtentLogger.fail("Ambiguity Details: " + result.getError().toString());
                        LOG.error(color + "  Ambiguity Cause: " + ANSI_RESET, result.getError());
                    }
                    LOG.error(color + statusPrefix + status + ": " + stepDescription + ANSI_RESET);
                    break;
            }
        }
    }

    // --- Handler for Scenario Finished ---
    private void handleTestCaseFinished(TestCaseFinished event) {

        TestCase testCase = event.getTestCase();
        String testCaseName = testCase.getName();
        Result result = event.getResult();
        Status status = result.getStatus();

        String color = ANSI_RESET;
        String outcomePrefix = "Finished Scenario: ";

        switch (status) {
            case PASSED:
                color = ANSI_GREEN;
                break;
            case FAILED:
                color = ANSI_RED;
                break;
            case SKIPPED:
                color = ANSI_YELLOW;
                break;
            case PENDING:
                color = ANSI_YELLOW;
                break;
            case UNDEFINED:
                color = ANSI_YELLOW;
                break;
            case AMBIGUOUS:
                color = ANSI_YELLOW;
                break;
        }

        LOG.info(color + "*****************************************************************************************" + ANSI_RESET);
        LOG.info(color + outcomePrefix + testCaseName + " --> " + status.name() + ANSI_RESET);
        LOG.info(String.format("%s  Duration: %.2f seconds%s", color, result.getDuration().toMillis() / 1000.0, ANSI_RESET));
        LOG.info(color + "*****************************************************************************************" + ANSI_RESET);
        LOG.info("");

    }

    // --- Handler for Test Run Finished (Suite Finish) ---
    private void handleTestRunFinished(TestRunFinished event) {
        Result result = event.getResult(); // Overall result of the test run
        Status status = result.getStatus(); // This can be PASSED if all scenarios passed, FAILED otherwise
        String overallColor = (status == Status.PASSED) ? ANSI_GREEN : ANSI_RED;

        LOG.info(ANSI_PURPLE + "=========================================================================================" + ANSI_RESET);
        LOG.info(ANSI_PURPLE + " CUCUMBER TEST EXECUTION FINISHED " + ANSI_RESET);
        LOG.info(overallColor + " Overall Status: " + status.name() + ANSI_RESET);
        LOG.info(ANSI_PURPLE + " Timestamp: " + event.getInstant() + ANSI_RESET);
        LOG.info(ANSI_PURPLE + "=========================================================================================" + ANSI_RESET);

        ExtentReport.flushReports();
    }
}