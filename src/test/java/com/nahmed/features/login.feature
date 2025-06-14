@regression
Feature: Application Login
  As a registered user
  I want to log in to the application
  So I can access my personalized content

  @login
  Scenario Outline: <desc> Login test with different credentials
    Given User is on the application login page
    When User enters email "<email>" and password "<password>"
    And User click on the login button
    Then User should be navigated to the homepage "<pagename>"

    Examples:
      | desc | email                   | password | pagename            |
      | TC01 | demo.testfire@gmail.com | Demo@123 | Invalid Page Name   |
      | TC02 | demo.testfire@gmail.com | Demo@123 | Automation Practice |
