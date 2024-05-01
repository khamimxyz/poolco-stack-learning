Feature: Promotion Management
  As a Promotion Manager
  I want to be able to create and update promotions
  So that I can manage the promotion catalog efficiently

  Background:
    Given I am logged in as a Promotion Manager

  Scenario: Create a new promotion
    When I create a new promotion with the following details:
      | Name          | Discount |
      | Promo 1       | 5        |
    Then the promotion should be created successfully

  Scenario: Update an existing promotion
    Given there is an existing promotion with name "Promo 1"
    When I update the promotion with the following details:
      | Name          | Discount |
      | Promo 1       | 10       |
    Then the promotion should be updated successfully
