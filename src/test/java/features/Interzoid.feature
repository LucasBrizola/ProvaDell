Feature: get weather from a city
  I, as a customer, want to see the weather of a city through Interzoid api

  @API
  Scenario Outline: Get Weather from a city
    When i make a GET requisition to the API's endpoint for the city "<city>" and state "<state>"
    Then i will verify it's Status as "<expectedOutcome>"
    Examples:
      | city       | state | expectedOutcome |
      | Round Rock | TX    | 200             |
      | Tampa      | TX    | 404             |
      | --         | --    | 400             |