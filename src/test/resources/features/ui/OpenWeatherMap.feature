Feature: OpenWeather features

  Background: To Launch the browser
    Given Launch the browser

  @ui
  Scenario: Placeholder text on Homepage
    When I navigate to the homepage
    Then I wait until the homepage is loaded
    And  The main page's search field contains correct placeholder text

  @ui
  Scenario Outline: Placeholder text result: Sydney, AU
    When I navigate to the homepage
    Then I wait until the homepage is loaded
    And I search for <search_name>
    And I select <city_name> from the list
    And I verify the right data on the screen for city: <city_name>

    Examples:
      | search_name | city_name    |
      | "Sydney"    | "Sydney, AU" |
