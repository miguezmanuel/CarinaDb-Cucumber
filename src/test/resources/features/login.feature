Feature: Login on SauceDemo

  Scenario: Log in with a valid user
    Given the user "standard_user" exists in the database
    When the user logs into SauceDemo
    Then the products page should load successfully

  Scenario: Attempt to log in with an invalid user
    Given the user "invalid_user" does not exist in the database
    When the user tries to log into SauceDemo
    Then an invalid credentials error message should be displayed