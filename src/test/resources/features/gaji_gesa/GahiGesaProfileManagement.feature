@gaji_gesa
Feature: As a user I want to check Gaji Gesa Profile Management

  Scenario: As a user I want to create new profile
    Given I want to create a user in the Gaji Gesa
    When a user submit profile create request
    Then profile is created


  Scenario: As a user I want to update any information of newly created user
    Given I want to create a user in the Gaji Gesa
    When a user submit profile create request
    Then profile is created
    When User updated profile
    Then New profile detail is returned


  Scenario: Retrieve specific user information and update each field correctly
    Given User retrieve specific user 5001
    When A user updated all profile details of user 5001
    Then All new profile detail is returned

  Scenario: Reject user updates that have malformed request
    Given User retrieve specific user 5001
    When Updated all profile details with malformed request of user 5001
    Then Error malformed update is returned


  Scenario:  Verify if a deleted user doesn’t exist anymore
    Given I want to create a user in the Gaji Gesa
    When a user submit profile create request
    Then profile is created
    When User submits delete  newly created user
    Then Newly created user is deleted


