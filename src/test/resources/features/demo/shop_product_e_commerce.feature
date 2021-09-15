Feature: Sauce Demo Product

  @test
  Scenario Template: User is able to validate product name and price value
    Given User successfully login to e-commerce. Username: standard_user and password: secret_sauce
    When User is in the products screen
    Then User can verify that item <itemName> has a price tag of <price>
    Examples:
      | itemName                | price  |
      | Sauce Labs Backpack     | $29.99 |
      | Sauce Labs Bolt T-Shirt | $15.99 |


  @test
  Scenario: User has successfully added to cart and checkout
    Given User successfully login to e-commerce. Username: standard_user and password: secret_sauce
    And User is in the products screen
    And User add to cart item Sauce Labs Backpack
    When User proceed to check-out
    Then User able to complete place an order
