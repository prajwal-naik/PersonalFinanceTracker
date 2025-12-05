package StepDefs;

import io.cucumber.java.en.*;
import org.PersonalFinanceTracker.report.CategoryReport;
import org.PersonalFinanceTracker.report.IncomeExpenseReport;
import org.PersonalFinanceTracker.report.MonthlyReport;
import org.PersonalFinanceTracker.report.ReportService;
import org.PersonalFinanceTracker.transaction.Transaction;
import org.PersonalFinanceTracker.transaction.TransactionFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReportStepDefs {
    private List<Transaction> transactions = new ArrayList<>();
    private ReportService reportService = new ReportService();
    private String reportOutput;
    private Exception thrownException;

    @Given("I have the following transactions:")
    public void i_have_the_following_transactions(io.cucumber.datatable.DataTable dataTable) {
        TransactionFactory factory = new TransactionFactory();
        transactions.clear();

        dataTable.asMaps().forEach(row -> {
            String category = row.get("category");
            double amount = Double.parseDouble(row.get("amount"));
            String desc = row.get("description");
            LocalDate date = LocalDate.parse(row.get("date"));

            transactions.add(factory.createTransaction(category, amount, desc, date));
        });
    }

    @Given("I have no transactions")
    public void i_have_no_transactions() {
        transactions.clear();
    }

    @When("I select the {string}")
    public void i_select_the_report(String reportName) {
        switch (reportName) {
            case "Monthly Report":
                reportService.setStrategy(new MonthlyReport());
                break;
            case "Category Report":
                reportService.setStrategy(new CategoryReport());
                break;
            case "Income vs Expense Report":
                reportService.setStrategy(new IncomeExpenseReport());
                break;
            default:
                throw new IllegalArgumentException("Unknown report: " + reportName);
        }

        reportOutput = reportService.runReport(transactions);
    }

    @When("I run the report without selecting a strategy")
    public void i_run_the_report_without_selecting_strategy() {
        try {
            reportOutput = reportService.runReport(transactions);
        } catch (Exception e) {
            thrownException = e;
        }
    }

    @Then("the monthly report output should contain:")
    @Then("the category report output should contain:")
    @Then("the income vs expense report output should contain:")
    public void the_report_should_contain(String expectedOutput) {
        assertNotNull(reportOutput);
        assertTrue(reportOutput.contains(expectedOutput.trim()));
    }

    @Then("an error should be thrown with message {string}")
    public void error_should_be_thrown(String expectedMessage) {
        assertNotNull("Exception was not thrown", String.valueOf(thrownException));
        assertEquals(expectedMessage, thrownException.getMessage());
    }
}
