Feature: Order Management
  As a Order Manager
  I want to be able to create and update orders
  So that I can manage the order catalog efficiently

  Background:
    Given I am logged in as a Customer

  Scenario: Create a new order
    When I create a new order with the following details:
      | Name          | Category    | Price | Qty |
      | Product X     | Category X  | 100   | 1   |
    Then the order should be created successfully

