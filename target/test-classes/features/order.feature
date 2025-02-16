Scenario: User completes an order
Given the user has pending orders in the database
When the user adds their orders to the cart and proceeds to checkout
Then the order should be placed successfully