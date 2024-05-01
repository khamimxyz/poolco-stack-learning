Feature: Order Management
  As a Order Manager
  I want to be able to create and update orders
  So that I can manage the order catalog efficiently

  Background:
    Given I am logged in as a Order Manager

  Scenario: Create a new order
    When I create a new order with the following details:
      | Name          | Category    | Price | Promo Name | Discount |
      | Product X     | Category X  | 100   | Promo X    | 5        |
    Then the order should be created successfully

