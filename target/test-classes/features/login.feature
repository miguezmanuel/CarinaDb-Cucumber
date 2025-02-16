Feature: User Login and Order Placement

  Scenario: User logs in and places an order
    Given the user "standard_user" exists in the database
    When the user logs into SauceDemo
    Then the product page should be displayed
    And the user should see their orders

  Scenario: Another user logs in and places an order
    Given the user "problem_user" exists in the database
    When the user logs into SauceDemo
    Then the product page should be displayed
    And the user should see their orders