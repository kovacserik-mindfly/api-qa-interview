Feature: Description Rules

  @api
  Scenario: Checking the Description Field
    When I get the current weather data
    Then The description field is a fixed text with a suffix

  @api
  Scenario Outline: Checking Weather Rules
    Given the Fahrenheit temperature is set to <temperature>
    When I get the current weather data
    Then The weather should be <result>

    Examples:
      | temperature | result     |
      | 28          | "freezing" |
      | 41          | "cold"     |
      | 59          | "mild"     |
      | 70          | "warm"     |
      | 86          | "hot"      |