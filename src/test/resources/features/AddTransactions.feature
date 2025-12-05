Feature: Add a transaction
  As a user
  I want to add a transaction
  So that it appears in the list and persists in storage

  Scenario: Adding a valid transaction
    Given a fresh transaction manager
    When the user adds the following transactions:
      | category  | amount | description | date        |
      | food      | 12.50  | Lunch       | 2025-01-01  |
    Then the system should store the following transactions:
      | category  | amount | description | date        |
      | food      | 12.50  | Lunch       | 2025-01-01  |

  Scenario: Adding multiple transactions
    Given a fresh transaction manager
    When the user adds the following transactions:
      | category  | amount  | description   | date        |
      | food      | 12.50   | Lunch         | 2025-01-01  |
      | Transport | 10      | Bus           | 2025-01-02  |
    Then the system should store the following transactions:
      | category  | amount  | description   | date        |
      | food      | 12.50   | Lunch         | 2025-01-01  |
      | Transport | 10      | Bus           | 2025-01-02  |

