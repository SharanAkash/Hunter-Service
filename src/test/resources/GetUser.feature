Feature: Get User API Testing

  Scenario: Fetch all users from the API
    Given I have the user API endpoint
    When I send a GET request to fetch all users
#    Then I should receive a valid response
