# Web-BDD-Framework (by: Nisar Ahmed)

This is a comprehensive **BDD (Behavior-Driven Development)** test automation framework built with **Java, Selenium, Cucumber, and TestNG**. It's designed for testing web applications, providing robust, readable, and maintainable automated tests.

-----

## ✨ Features

  * **Behavior-Driven Development:** Utilizes Cucumber and Gherkin syntax (`.feature` files) for clear, business-readable test scenarios.
  * **Page Object Model (POM):** Organizes UI elements and interactions into separate page classes for better maintainability and code reuse.
  * **Parallel Execution:** Supports running tests in parallel using TestNG's data provider to significantly reduce execution time.
  * **Multi-Environment Support:** Easily switch between different test environments (e.g., INT, CERT) using a single configuration property.
  * **Detailed Reporting:** Generates beautiful, interactive HTML reports with **ExtentReports**, including screenshots for failed/skipped steps.
  * **Custom Listeners:** A powerful `TestListener` provides real-time, color-coded console logs for test runs and granular control over report generation.
  * **Robust Waits:** Implements an `ExplicitWaitFactory` to handle dynamic elements and avoid flaky tests.
  * **Centralized Driver Management:** Uses `ThreadLocal` to manage WebDriver instances, ensuring thread safety during parallel execution.
  * **Rerun Failed Tests:** Automatically generates a `rerun_data.txt` file to easily re-execute only the failed scenarios.
  * **Utility-Driven:** Includes helper classes for common tasks like property management, dynamic XPath generation, assertions, and more.

-----

## 🛠️ Tech Stack

  * **Language:** Java 17+
  * **Test Runner:** TestNG
  * **BDD Tool:** Cucumber
  * **Browser Automation:** Selenium WebDriver
  * **Build Tool:** Maven
  * **Reporting:** ExtentReports
  * **Logging:** Logback / SLF4J

-----

## 📂 Project Structure

The framework follows a standard Maven project structure with a clear separation of concerns.

```
Web-BDD-Framework/
├── reports/                          # Output directory for logs and reports
│   ├── cucumber/
│   ├── extent/
│   └── logs/
├── src/
│   ├── test/
│   │   ├── java/
│   │   │   └── com/nahmed/
│   │   │       ├── constants/        # Framework-level constants (e.g., file paths)
│   │   │       ├── driver/           # WebDriver management (Driver, DriverManager)
│   │   │       ├── enums/            # Enumerations for type-safe configuration (ConfigProperties)
│   │   │       ├── events/           # Cucumber Hooks (Before/After scenarios)
│   │   │       ├── exceptions/       # Custom exception classes
│   │   │       ├── factories/        # Factories for creating objects (e.g., ExplicitWaitFactory)
│   │   │       ├── features/         # Gherkin feature files
│   │   │       ├── listeners/        # Custom test listeners for logging and reporting
│   │   │       ├── pageobjects/      # Page Object Model classes
│   │   │       ├── reports/          # ExtentReports configuration and management
│   │   │       ├── runners/          # TestNG test runners for Cucumber
│   │   │       ├── stepdefinitions/  # Step implementation for Gherkin features
│   │   │       └── utils/            # Helper and utility classes
│   │   └── resources/
│   │       ├── config.properties     # Main configuration file for the framework
│   │       ├── cucumber.properties   # Cucumber-specific settings
│   │       └── logback.xml           # Logging configuration
└── pom.xml                           # Maven project configuration
```

-----

## 🚀 Getting Started

### Prerequisites

Before you begin, ensure you have the following installed:

  * **Java JDK 17** or higher
  * **Apache Maven**
  * An IDE like **IntelliJ IDEA** or **Eclipse**

### Setup

1.  **Clone the repository:**

    ```bash
    git clone https://github.com/nisarahmedqae/Web-BDD-Framework.git
    ```

2.  **Navigate to the project directory:**

    ```bash
    cd Web-BDD-Framework
    ```

3.  **Install dependencies:** Maven will automatically download all the required dependencies defined in `pom.xml`.

    ```bash
    mvn clean install
    ```

-----

## ⚙️ Configuration

All major framework settings are managed in `src/test/resources/config.properties`.

### Switching Environments

You can override the default environment set in `config.properties` by passing a system property during the Maven build.

To run tests on the **CERT** environment:

```bash
mvn clean test -Denv=CERT
```

-----

## ▶️ How to Run Tests

### Running via Maven

You can execute tests from the command line using Maven. The `TestRunner.java` class controls which tests are run via its `tags` attribute.

1.  **Run a specific tag (e.g., `@login`):** Modify the `tags` attribute in `src/test/java/com/nahmed/runners/TestRunner.java` and then run:

    ```bash
    mvn clean test
    ```

2.  **Run all tests:** To run all feature files, simply remove the `tags` attribute from `TestRunner.java`.

### Running Failed Tests

The framework is configured to automatically capture failed scenarios in `reports/cucumber/rerun_data.txt`. You can run only these failed tests using `FailedTestRunner.java`.

To run only the failed tests from the last run:

```bash
mvn clean test -Dcucumber.features="@reports/cucumber/rerun_data.txt"
```

### Parallel Execution

Parallel execution is enabled by default in the runners (`TestRunner.java` and `FailedTestRunner.java`) via TestNG's data provider. The number of parallel threads can be configured within these runner files.

-----

## 📊 Reporting and Logging

After a test run, the following reports and logs are generated in the `reports/` directory:

  * **ExtentReports:** A detailed, interactive HTML report.
      * **Location:** `reports/extent/extent_report.html`
  * **Cucumber HTML Report:** A standard report generated by Cucumber.
      * **Location:** `reports/cucumber/bdd_report.html`
  * **Execution Log:** A text file containing detailed logs from the test run, configured by `logback.xml`.
      * **Location:** `reports/logs/test_execution.log`

-----

## ✍️ Writing New Tests

Follow these steps to add a new test case:

1.  **Create a Feature File:** Add a new `.feature` file in `src/test/java/com/nahmed/features/`. Write your scenarios using Gherkin syntax.
2.  **Create Page Objects:** For any new pages your test interacts with, create a corresponding class in `com.nahmed.pageobjects`. Add locators (using `By`) and methods that represent user actions on that page.
3.  **Implement Step Definitions:** Create a new class in `com.nahmed.stepdefinitions`. Implement the Java methods that map to your Gherkin steps. Use your Page Object methods here to interact with the application.

-----
