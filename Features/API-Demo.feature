Feature: API Sample Demo Test Automation

  Scenario: TEST_001 - GET Users
    When User execute GET users API
    Then User verify response code 200
    Then User verify response contains "Baalaaditya Kocchar"

  Scenario: TEST_002 - GET Comments
    When User execute GET Comments API
    Then User verify response code 200
    Then User verify response contains "Javas Desai"