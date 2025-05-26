Feature: Application Login
  As a registered user
  I want to log in to the application
  So I can access my personalized content

  Scenario Outline: Successful login with valid credentials
    Given I am on the application login page
    When I enter username "<username>" and password "<password>"
    And I click on the login button
    Then I should be navigated to the dashboard
    And I should see a welcome message containing "<username>"

    Examples:
      | username         | password   |
      | testuser1@app.com | P@sswOrd1! |
      | testuser2@app.com | P@sswOrd2! |

  Scenario: Unsuccessful login with invalid credentials
    Given I am on the application login page
    When I enter username "invaliduser@app.com" and password "wrongpassword"
    And I click on the login button
    Then I should see an error message "Invalid username or password."
