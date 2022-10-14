Feature: City Rules

  @api
  Scenario: Checking City Rules
    When I get the current weather data
    Then The city field would give a fixed city name as a string