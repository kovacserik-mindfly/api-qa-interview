Feature: Weather Object Rules

  @api
  Scenario: Checking the weather object
    When I get the current weather data
    Then The weather object contains the temperature in both Fahrenheit and Celsius

  @api
  Scenario Outline: Checking the tempInFahrenheit in weather object
    When I get the current weather data
    Then The <field> field contains the current temperature in Fahrenheits
    Examples:
      | field              |
      | "tempInFahrenheit" |

  @api
  Scenario Outline: Checking the tempInCelsius in weather object
    When I get the current weather data
    Then The <field> field contains the current temperature in Celsius
    And The tempInCelsius Calculated from the tempInFahrenheit field
    Examples:
      | field           |
      | "tempInCelsius" |


  @api
  Scenario Outline: Checking the temperature converter in weather object
    Given the Fahrenheit temperature is set to <Fahrenheit>
    When I get the current weather data
    And The Celsius value should be <Celsius>
    Examples:
      | Fahrenheit | Celsius |
      | 32         | 0       |
      | 41         | 5       |
      | 50         | 10      |
      | 68         | 20      |


