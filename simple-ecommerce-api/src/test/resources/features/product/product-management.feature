Feature: Product Management
  As a Product Manager
  I want to be able to create and update products
  So that I can manage the product catalog efficiently

  Background:
    Given I am logged in as a Product Manager

  Scenario: Create a new product
    When I create a new product with the following details:
      | Name          | Category | Price |
      | Beautiful Bag | Bag      | 300   |
    Then the product should be created successfully

  Scenario: Update an existing product
    Given there is an existing product with name "Beautiful Bag"
    When I update the product with the following details:
      | Name          | Category | Price |
      | Beautiful Bag | Bag      | 250   |
    Then the product should be updated successfully
