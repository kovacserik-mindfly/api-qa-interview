Feature: Schema Validation Rules

  @api
  Scenario: Checking the schema in the response
    When I get the current weather data
    Then The response should match to the schema