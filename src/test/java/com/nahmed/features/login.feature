Feature: Application Login
  As a registered user
  I want to log in to the application
  So I can access my personalized content

  @login
  Scenario Outline: Login test with different credentials "<description>"
    Given User is on the application login page
    When User enters email "<email>" and password "<password>"
    And User click on the login button
    Then User should be navigated to the homepage "<pagename>"

    Examples:
      | description      | email                   | password | pagename            |
      | Invalid PageName | demo.testfire@gmail.com | Demo@123 | Invalid Page Name   |
      | Valid PageName   | demo.testfire@gmail.com | Demo@123 | Automation Practice |
