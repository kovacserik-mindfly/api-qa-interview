Feature: Condition Rules

  @api
  Scenario Outline: Checking ConditionId Rules
    Given the conditionId is set to <conditionId>
    When I get the current weather data
    Then The condition should be <result>

    Examples:
      | conditionId | result    |
      | 1           | "clear"   |
      | 2           | "windy"   |
      | 3           | "mist"    |
      | 4           | "drizzle" |
      | 5           | "dust"    |