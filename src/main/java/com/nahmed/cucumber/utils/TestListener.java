package com.nahmed.cucumber.utils;

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
    // Optional: Yellow for skipped/pending?
    // public static final String ANSI_YELLOW = "\u001B[33m";

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestCaseFinished.class, this::handleTestCaseFinished);
        publisher.registerHandlerFor(TestStepStarted.class, this::handleTestStepStarted);
        publisher.registerHandlerFor(TestStepFinished.class, this::handleTestStepFinished);
        publisher.registerHandlerFor(TestCaseStarted.class, this::handleTestCaseStarted);
    }

    // --- Handler for Scenario Started ---
    private void handleTestCaseStarted(TestCaseStarted event) {
        TestCase testCase = event.getTestCase();
        String scenarioName = testCase.getName();
        LOG.info(ANSI_BLUE + "*****************************************************************************************" + ANSI_RESET);
        // Apply BLUE color to "Starting Scenario" line
        LOG.info(ANSI_BLUE + "Starting Scenario: " + scenarioName + ANSI_RESET);
        LOG.info(ANSI_BLUE + "*****************************************************************************************" + ANSI_RESET);
    }


    // --- Handler for Step Started ---
    private void handleTestStepStarted(TestStepStarted event) {
        TestStep testStep = event.getTestStep();
        if (testStep instanceof PickleStepTestStep) {
            PickleStepTestStep pickleStep = (PickleStepTestStep) testStep;
            String stepText = pickleStep.getStep().getText();
            String keyword = pickleStep.getStep().getKeyword();
            // Apply BLUE color to "STEP STARTED" line
            LOG.info(ANSI_BLUE + "STEP STARTED: " + keyword.trim() + " " + stepText + ANSI_RESET);
        }
    }

    // --- Handler for Step Finished ---
    private void handleTestStepFinished(TestStepFinished event) {
        TestStep testStep = event.getTestStep();
        Result result = event.getResult();
        Status status = result.getStatus();

        if (testStep instanceof PickleStepTestStep) {
            PickleStepTestStep pickleStep = (PickleStepTestStep) testStep;
            String stepText = pickleStep.getStep().getText();
            String keyword = pickleStep.getStep().getKeyword();
            String stepDescription = keyword.trim() + " " + stepText;

            // Apply color based on status
            String color = ANSI_RESET; // Default: no color
            switch (status) {
                case PASSED:
                    color = ANSI_GREEN;
                    LOG.info(color + "STEP " + status + ": " + stepDescription + ANSI_RESET);
                    break;
                case FAILED:
                    color = ANSI_RED;
                    LOG.info(color + "STEP " + status + ": " + stepDescription + ANSI_RESET); // Log status line in red

                    // Log the specific failure message in RED
                    Throwable error = result.getError();
                    String errorMessage = "No specific error message available.";
                    if (error != null) {
                        errorMessage = error.getMessage() != null ? error.getMessage().split(System.lineSeparator())[0] : error.toString();
                    }
                    LOG.error(color + "STEP FAILED: [" + stepDescription + "] | Error: " + (error != null ? error.getClass().getName() : "Unknown Error") + ": " + errorMessage + ANSI_RESET);
                    break;
                case SKIPPED:
                case PENDING:
                case UNDEFINED:
                case AMBIGUOUS:
                    // Optional: Use Yellow or another color for these statuses
                    // color = ANSI_YELLOW;
                    // LOG.info(color + "STEP " + status + ": " + stepDescription + ANSI_RESET);
                    // Or just log without specific color for now:
                    LOG.info("STEP " + status + ": " + stepDescription);
                    break;
            }
        }
    }


    // --- Handler for Scenario Finished ---
    private void handleTestCaseFinished(TestCaseFinished event) {
        TestCase testCase = event.getTestCase();
        Result result = event.getResult();
        Status status = result.getStatus();
        Throwable error = result.getError();
        String scenarioName = testCase.getName();

        // Determine color based on final status
        String color = (status == Status.PASSED) ? ANSI_GREEN : ANSI_RED; // Green for PASSED, Red for others

        LOG.info(color + "*****************************************************************************************" + ANSI_RESET);
        // Apply color to "Finished Scenario" line
        LOG.info(color + "Finished Scenario: " + scenarioName + " --> " + status.name() + ANSI_RESET);
        if (error != null) {
            // Log error details also in RED
            LOG.error(ANSI_RED + "Scenario Error Details (Full Stack Trace): " + ANSI_RESET, error);
        }
        LOG.info(color + "*****************************************************************************************" + ANSI_RESET);
    }
}