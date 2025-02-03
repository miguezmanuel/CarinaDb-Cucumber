Feature: Order processing in SauceDemo

  Scenario: User places an order successfully
    Given the user "standard_user" exists in the database
    And the user has pending orders in the database
    When the user adds their orders to the cart and proceeds to checkout
    Then the order should be placed successfully