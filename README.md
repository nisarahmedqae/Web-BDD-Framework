# Web-BDD-Framework : Complete Documentation

## Framework Overview

This framework is designed for automating web application testing using Behavior-Driven Development (BDD) approach with Cucumber, TestNG, and Selenium. It provides a structured way to write, execute, and report automated tests with built-in configuration management and detailed reporting.

## Key Components and Flow

### 1. Test Execution Flow

```
TestRunner (Triggers) 
  → Cucumber Features 
    → Step Definitions 
      → Page Objects 
        → BrowserService (Wrapper for Selenium operations)
          → Explicit Wait Factory
          → Driver Manager
```

### 2. Configuration Management

- **`config.properties`**: Central configuration file for all test parameters
- **`PropertyUtils.java`**: Utility to read properties with static initialization
- **Environment Handling**:
    - Uses `TestContext.java` to manage environment-specific configurations
    - Supports INT and CERT environments with different URLs and credentials

### 3. Driver Management

- **`Driver.java`**: Initializes and quits WebDriver instances
- **`DriverManager.java`**: Thread-safe WebDriver management using ThreadLocal
- Supported browsers: Chrome (including headless) and Edge

### 4. Page Object Model

- Each page (e.g., `LoginPage.java`, `HomePage.java`) contains:
    - Locators (By elements)
    - Methods representing user actions
    - Built-in waits and logging

### 5. Wait Strategies

- **`ExplicitWaitFactory.java`**: Factory for different wait conditions:
    - CLICKABLE
    - PRESENCE
    - VISIBLE
    - NONE
- Implicit wait configured via `config.properties`

### 6. Reporting System

- **Extent Reports** with:
    - Screenshots for failed/passed/skipped steps (configurable)
    - Thread-safe implementation using `ExtentManager.java`
    - Scenario and step-level reporting
- **Logging** with Logback:
    - Console and file output
    - Color-coded output in console

### 7. Utility Classes

- **`BrowserService.java`**: Wrapper for common Selenium operations
- **`DynamicXpathUtils.java`**: Handles dynamic XPath generation
- **`ScreenshotUtils.java`**: Captures screenshots as base64
- **`DecodeUtils.java`**: Handles base64 decoding

## How to Use the Framework

### 1. Prerequisites

- Java 17
- Maven
- Chrome/Edge browser installed
- Browser drivers in system PATH

### 2. Setup

1. Clone the repository
2. Configure `config.properties`:
   ```properties
   browser=chrome
   environment=int
   url_int=https://your-test-url.com
   username_int=testuser
   password_int=testpass
   ```
3. Install dependencies:
   ```bash
   mvn clean install
   ```

### 3. Writing Tests

#### Feature Files (BDD)

```gherkin
@regression
Feature: Application Login
  Scenario: Valid login
    Given User is on the application login page
    When User enters valid credentials
    Then User should be navigated to the homepage
```

#### Step Definitions

```java
public class LoginSteps {
    @Given("User is on the application login page")
    public void userIsOnLoginPage() {
        browserService.openUrl(PropertyUtils.getValue("url"));
    }
}
```

#### Page Objects

```java
public class LoginPage {
    private final By emailTextbox = By.id("userEmail");
    
    public LoginPage enterEmail(String email) {
        browserService.sendKeys(emailTextbox, email, WaitStrategy.VISIBLE, 10, "Email");
        return this;
    }
}
```

### 4. Running Tests

#### From Command Line

```bash
mvn clean test
```

#### With Specific Environment

```bash
mvn test -Denv=cert
```

#### Running Specific Tags

Modify `testng.xml` or use Cucumber options:

```java
@CucumberOptions(tags = "@login")
```

### 5. Viewing Results

After execution, reports are generated in:
- `reports/extent-test-output/index.html` (or timestamped version)
- `reports/logs/web-tests.log`

## Key Configuration Options

| Property | Values | Description |
|----------|--------|-------------|
| browser | chrome, edge | Browser for test execution |
| over_ride_reports | yes/no | Overwrite previous report or create new |
| passed_steps_screenshots | yes/no | Include screenshots for passed steps |
| failed_steps_screenshots | yes/no | Include screenshots for failed steps |
| retry_failed_tests | yes/no | Enable test retry for failures |
| max_retries_count | number | Maximum retry attempts |

## Best Practices

1. **Page Objects**:
    - Keep locators private
    - Return page objects for method chaining
    - Use explicit waits consistently

2. **Step Definitions**:
    - Keep business logic in page objects
    - Use dependency injection for shared state

3. **Reporting**:
    - Add descriptive messages in ExtentLogger
    - Configure screenshot preferences in properties

4. **Configuration**:
    - Keep sensitive data out of version control
    - Use environment variables for secrets in CI/CD

This framework provides a solid foundation for web automation testing with BDD that can be easily extended for your specific needs.