package StepDefs;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.PersonalFinanceTracker.*;
import org.PersonalFinanceTracker.exporter.*;
import org.PersonalFinanceTracker.io.FixedIO;
import org.PersonalFinanceTracker.report.ReportService;
import org.PersonalFinanceTracker.transaction.*;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionStepDefs {

    private FixedIO io;
    private TransactionManager manager;
    private TransactionFactory factory;
    private ReportService reportService;
    private ExportService exportService;
    private Console ui;

//    @Before
//    public void beforeScenario() {
//        try {
//            File f = new File("testdata.txt");
//            if (f.exists()) {
//                Files.write(f.toPath(), new byte[0]);
//            } else {
//                f.createNewFile();
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to clear testdata.txt", e);
//        }
//    }

    // Constructor for setup
    public TransactionStepDefs() {
        try {
            File f = new File("testdata.txt");
            if (f.exists()) {
                Files.write(f.toPath(), new byte[0]);
            } else {
                f.createNewFile();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to clear testdata.txt", e);
        }

        io = new FixedIO();
        factory = new TransactionFactory();
        manager = new TransactionManager("testdata.txt", factory);
        reportService = new ReportService();
        exportService = new ExportService();
        ui = new Console(io, manager, factory, reportService, exportService);
    }

    @Given("a fresh transaction manager")
    public void freshTransactionManager() {}

    @Given("a fresh transaction manager with the following transactions:")
    public void freshTransactionManagerWithTheFollowingTransactions(io.cucumber.datatable.DataTable data) {
        List<Map<String, String>> rows = data.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            String category = row.get("category");
            double amount = Double.parseDouble(row.get("amount"));
            String description = row.get("description");
            String dateString = row.get("date");
            LocalDate date;
            if (dateString == null) {
                date = LocalDate.now();
            } else {
                date = LocalDate.parse(dateString);
            }
            Transaction t = factory.createTransaction(category, amount, description, date);
            manager.addTransaction(t);
        }
    }

    @When("the user adds the following transactions:")
    public void theUserAddsTheFollowingTransactions(io.cucumber.datatable.DataTable data) {
        List<Map<String, String>> rows = data.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            io.addInput("1");
            String category = row.get("category");
            io.addInput(category);
            String amount = row.get("amount");
            io.addInput(amount);
            String description = row.get("description");
            io.addInput(description);
            String date = row.get("date");
            if (date == null) {
                date = "\n";
            }
            io.addInput(date);
        }
//        io.addInput("0");
        ui.start();
    }

    @And("the user lists all transactions")
    public void theUserListsAllTransactions() {
        io.addInput("2");
        ui.start();
    }

    @Then("the system should store the following transactions:")
    public void theSystemShouldStoreTheFollowingTransaction(io.cucumber.datatable.DataTable data) {
        List<Map<String, String>> expectedRows = data.asMaps(String.class, String.class);
        List<Transaction> actualTransactions = manager.getAllTransactions();
        assertEquals(expectedRows.size(), actualTransactions.size());

        for (int i = 0; i < expectedRows.size(); i++) {
            Map<String, String> expected = expectedRows.get(i);
            Transaction actual = actualTransactions.get(i);

            if (!actual.getCategory().equalsIgnoreCase(expected.get("category"))) {
                throw new AssertionError("Transaction " + (i+1) + " category mismatch: expected "
                        + expected.get("category") + " but was " + actual.getCategory());
            }

            double expectedAmount = Double.parseDouble(expected.get("amount"));
            if (actual.getAmount() != expectedAmount) {
                throw new AssertionError("Transaction " + (i+1) + " amount mismatch: expected "
                        + expectedAmount + " but was " + actual.getAmount());
            }

            if (!actual.getDescription().equals(expected.get("description"))) {
                throw new AssertionError("Transaction " + (i+1) + " description mismatch: expected "
                        + expected.get("description") + " but was " + actual.getDescription());
            }

            String expectedDate = expected.get("date");
            if (expectedDate == null) {
                expectedDate = LocalDate.now().toString();
            }
            if (!actual.getDate().toString().equals(expectedDate)) {
                throw new AssertionError("Transaction " + (i+1) + " date mismatch: expected "
                        + expected.get("date") + " but was " + actual.getDate());
            }
        }
    }
}
