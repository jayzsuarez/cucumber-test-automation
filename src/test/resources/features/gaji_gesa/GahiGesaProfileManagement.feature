Feature: As a user I want to check Gaji Gesa Profile Management

  @gaji_gesa
  Scenario: As a user I want to check profile Management
    Given I want to create a user in the Gaji Gesa
    When a user submit profile create request
    Then profile is created