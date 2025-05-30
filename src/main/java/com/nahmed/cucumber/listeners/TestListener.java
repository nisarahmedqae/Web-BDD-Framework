package com.nahmed.cucumber.listeners;

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
    public static final String ANSI_YELLOW = "\u001B[33m"; // For skipped/pending/undefined
    public static final String ANSI_PURPLE = "\u001B[35m"; // For overall run status

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
        // Add any global setup logic here if needed (e.g., initializing a custom report)
    }


    // --- Handler for Scenario Started ---
    private void handleTestCaseStarted(TestCaseStarted event) {
        TestCase testCase = event.getTestCase();
        String scenarioName = testCase.getName();
        String featureName = testCase.getUri().toString().substring(testCase.getUri().toString().lastIndexOf('/') + 1);

        LOG.info(ANSI_BLUE + "*****************************************************************************************" + ANSI_RESET);
        LOG.info(ANSI_BLUE + "Feature: " + featureName + ANSI_RESET);
        LOG.info(ANSI_BLUE + "Starting Scenario: " + scenarioName + " (Line: " + testCase.getLine() + ")" + ANSI_RESET);
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
            // Log with a less prominent color or just plain
            LOG.info("  " + keyword.trim() + " " + stepText);
        } else if (testStep instanceof HookTestStep) {
            HookTestStep hookStep = (HookTestStep) testStep;
            LOG.debug("HOOK STARTED: " + hookStep.getHookType().name());
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

            String color = ANSI_RESET;
            String statusPrefix = "  STEP ";

            switch (status) {
                case PASSED:
                    color = ANSI_GREEN;
                    LOG.info(color + statusPrefix + status + ": " + stepDescription + ANSI_RESET);
                    break;
                case FAILED:
                    color = ANSI_RED;
                    LOG.error(color + statusPrefix + status + ": " + stepDescription + ANSI_RESET); // Log status line in red

                    Throwable error = result.getError();
                    String errorMessage = "No specific error message available.";
                    if (error != null) {
                        // Get the first line of the error message for a concise summary
                        errorMessage = error.getMessage() != null ? error.getMessage().split(System.lineSeparator())[0] : error.toString();
                        // Log the full stack trace for detailed debugging if needed, or rely on scenario finish for full trace
                        LOG.error(color + "    Error: " + error.getClass().getName() + ": " + errorMessage + ANSI_RESET);
                        // For very detailed step failure, you could log the full stack trace here too:
                        // LOG.error("Step Failure Stack Trace:", error);
                    } else {
                        LOG.error(color + "    Error: Unknown error, no Throwable provided." + ANSI_RESET);
                    }
                    break;
                case SKIPPED:
                    color = ANSI_YELLOW;
                    LOG.info(color + statusPrefix + status + ": " + stepDescription + ANSI_RESET);
                    break;
                case PENDING:
                case UNDEFINED:
                case AMBIGUOUS:
                    color = ANSI_YELLOW; // Or a different color like magenta
                    LOG.warn(color + statusPrefix + status + ": " + stepDescription + ANSI_RESET);
                    if (result.getError() != null) {
                        LOG.warn(color + "    Reason: " + result.getError().getMessage().split(System.lineSeparator())[0] + ANSI_RESET);
                    }
                    break;
                default:
                    LOG.info(statusPrefix + status + ": " + stepDescription);
            }
        } else if (testStep instanceof HookTestStep) {
            HookTestStep hookStep = (HookTestStep) testStep;
            String hookLocation = hookStep.getCodeLocation() != null ? hookStep.getCodeLocation() : "Unknown Location";
            if (status == Status.FAILED) {
                LOG.error(ANSI_RED + "  HOOK " + hookStep.getHookType().name() + " FAILED at " + hookLocation + ANSI_RESET);
                if(result.getError() != null) {
                    LOG.error(ANSI_RED + "    Hook Error: " + result.getError().getMessage().split(System.lineSeparator())[0] + ANSI_RESET, result.getError());
                }
            } else {
                LOG.debug("  HOOK " + hookStep.getHookType().name() + " " + status + " at " + hookLocation);
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
            default: // For PENDING, UNDEFINED, AMBIGUOUS
                color = ANSI_YELLOW;
                break;
        }

        LOG.info(color + "*****************************************************************************************" + ANSI_RESET);
        LOG.info(color + outcomePrefix + scenarioName + " --> " + status.name() + ANSI_RESET);
        if (error != null) {
            // Log error details also in the determined color (e.g., RED for FAILED)
            LOG.error(color + "  Scenario Error Summary: " + error.getClass().getName() + ": " +
                    (error.getMessage() != null ? error.getMessage().split(System.lineSeparator())[0] : "No message") +
                    ANSI_RESET);
            // Log the full stack trace for failures
            if (status == Status.FAILED) {
                LOG.error(ANSI_RED + "  Full Stack Trace for Scenario: " + scenarioName + ANSI_RESET, error);
            }
        }
        LOG.info(String.format("%s  Duration: %.2f seconds%s", color, result.getDuration().toMillis() / 1000.0, ANSI_RESET));
        LOG.info(color + "*****************************************************************************************" + ANSI_RESET);
        LOG.info(""); // Add a blank line for separation
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
        // You could add summary statistics here if you collect them (e.g., total passed/failed/skipped scenarios)
        // For example, you might need to count them in handleTestCaseFinished and store in instance variables.
        LOG.info(ANSI_PURPLE + "=========================================================================================" + ANSI_RESET);

        if (result.getError() != null) {
            LOG.error(ANSI_RED + "A critical error occurred during the test run (e.g., in a @BeforeAll/@AfterAll hook):" + ANSI_RESET, result.getError());
        }
        // Add any global teardown logic here (e.g., closing report files)
    }
}