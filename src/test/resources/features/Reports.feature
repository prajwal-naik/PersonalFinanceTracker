Feature: Report Generation
  As a user of the Personal Finance Tracker
  I want to generate different types of reports
  So that I can analyze my spending and income patterns

  Scenario: Generate a monthly report when transactions exists across different months
    Given I have the following transactions:
      | category | amount | description | date       |
      | food     | 20.00  | lunch       | 2025-01-10 |
      | rent     | 800.00 | january rent| 2025-01-01 |
      | food     | 15.00  | dinner      | 2025-02-03 |
    When I select the "Monthly Report"
    Then the monthly report output should contain:
    """
    2025-01 : 820.00
    2025-02 : 15.00
    """

  Scenario: Generate income vs expense report
    Given I have the following transactions:
      | category | amount | description     | date       |
      | salary   | -3000  | january paycheck| 2025-01-01 |
      | food     | 50     | groceries       | 2025-01-10 |
      | rent     | 900    | rent payment    | 2025-01-02 |
    When I select the "Income vs Expense Report"
    Then the income vs expense report output should contain:
    """
    Total Income : 3000.00
    Total Expense: 950.00
    Net (Income - Expense): 2050.00
    """

  Scenario: Generate a report when no transactions exist
    Given I have no transactions
    When I select the "Monthly Report"
    Then the monthly report output should contain:
    """
    No transactions.
    """

  Scenario: Fail to generate report when no strategy is selected
    Given I have the following transactions:
      | category | amount | description | date       |
      | food     | 20.00  | lunch       | 2025-01-10 |
    When I run the report without selecting a strategy
    Then an error should be thrown with message "Report strategy not set"