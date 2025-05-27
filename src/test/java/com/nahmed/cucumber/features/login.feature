Feature: Application Login
  As a registered user
  I want to log in to the application
  So I can access my personalized content

  @login
  Scenario Outline: Successful login with valid credentials "<description>"
    Given User is on the application login page
    When User enters email "<email>" and password "<password>"
    And User click on the login button
    Then User should be navigated to the homepage "<pagename>"

    Examples:
      | description      | email                   | password   | pagename            |
      | Wrong Password   | demo.testfire@gmail.com | P@sswOrd1! | Automation Practice |
      | Invalid Username | testuser2@app.com       | Demo@123   | Automation Practice |
      | Wrong PageName   | testuser2@app.com       | Demo@123   | Automation Practice |
      | Valid Details    | demo.testfire@gmail.com | Demo@123   | Automation Practice |
