Feature: List a transaction
  As a user
  I want to be able to add a transaction
  I want to be able to list all transactions


  Scenario: Listing transactions after adding a transaction
    Given a fresh transaction manager with the following transactions:
      | category  | amount | description | date        |
      | transport | 10.00  | Bus         |             |
    When the user adds the following transactions:
      | category  | amount | description | date        |
      | food      | 12.50  | Lunch       | 2025-01-01  |
    And the user lists all transactions
    Then the system should store the following transactions:
      | category  | amount | description | date        |
      | transport | 10.00  | Bus         |             |
      | food      | 12.50  | Lunch       | 2025-01-01  |

