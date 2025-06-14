@regression
Feature: Adding products to cart
  As a registered user
  I want to add products to cart
  So that I can buy them

  @addingProductToCart
  Scenario Outline: <desc> Adding "<productName>" to the cart
    Given the user is logged into the application
    When the user clicks on Add to Cart button for product "<productName>"
    And the user navigates to the Cart page
    Then the product "<productName>" should be visible in the cart
    And the product "<productName>" price should be "<price>"

    Examples:
      | desc | productName     | price  |
      | TC01 | IPHONE 13 PRO   | 231500 |
      | TC02 | ZARA COAT 3     | 31500  |
      | TC03 | ADIDAS ORIGINAL | 31500  |
