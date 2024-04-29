Feature: Goods API

  Scenario: Get all goods
    When I request GET /api/goods
    Then the response status code should be 200

  Scenario: Insert new goods
    When I send a POST request to the "/api/goods" endpoint with the following JSON body:
      """
      {
        "name": "Product A",
        "quantity": 10
      }
      """
    Then the response status code should be 201
    And the response should contain the details of the created goods
    And the goods list should contain "Product A" with quantity 10

  Scenario: Get a specific good by ID
    When I request GET /api/goods/1
    Then the response status code should be 200

  # Add more scenarios for other API endpoints (POST, PUT, DELETE) as needed