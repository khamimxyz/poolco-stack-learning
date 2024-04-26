Feature: Product Management
  As a Product Manager
  I want to be able to create and update products
  So that I can manage the product catalog efficiently

  Background:
    Given I am logged in as a Product Manager

  Scenario: Create a new product
    When I create a new product with the following details:
      | Name          | Category | Price  |
      | Beautiful Bag | Bag      | 300000 |
    Then the product "Beautiful Bag" should be created successfully

  Scenario: Update an existing product
    Given there is an existing product with name "Beautiful Bag"
    When I update the product with name "Beautiful Bag" with the following details:
      | Category  | Price    |
      | Women Bag | 250000   |
    Then the product "Beautiful Bag" should be updated successfully
