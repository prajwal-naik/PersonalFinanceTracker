package org.PersonalFinanceTracker;

import org.PersonalFinanceTracker.report.CategoryReport;
import org.PersonalFinanceTracker.transaction.Transaction;
import org.PersonalFinanceTracker.transaction.categories.FoodTransaction;
import org.PersonalFinanceTracker.transaction.categories.RentTransaction;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategoryReportTest {

    @Test
    void testReportGroupsAndSumsCorrectly() {
        CategoryReport report = new CategoryReport();

        List<Transaction> tx = List.of(
                new FoodTransaction(10.0, "snacks", LocalDate.of(2025,1,1)),
                new FoodTransaction(5.0, "fruit", LocalDate.of(2025,1,2)),
                new RentTransaction(1000.0, "monthly rent", LocalDate.of(2025,1,3))
        );

        String output = report.generateReport(tx);

        // Mandatory header
        assertTrue(output.contains("=== Spending by Category ==="));

        // Food total = 10 + 5
        assertTrue(output.contains("Food : 15.00"));

        // Rent total = 1000
        assertTrue(output.contains("Rent : 1000.00"));
    }

    @Test
    void testReportSortsDescendingByTotal() {
        CategoryReport report = new CategoryReport();

        List<Transaction> tx = List.of(
                new FoodTransaction(20.0, "burger", LocalDate.now()),
                new RentTransaction(1000.0, "rent", LocalDate.now()),
                new FoodTransaction(5.0, "snack", LocalDate.now())
        );

        String output = report.generateReport(tx);

        // Rent(1000) should come BEFORE Food(25)
        int rentIndex = output.indexOf("Rent : 1000.00");
        int foodIndex = output.indexOf("Food : 25.00");

        assertTrue(rentIndex < foodIndex, "Rent should appear before Food because it has larger total.");
    }

    @Test
    void testEmptyTransactionsPrintsNoTransactionsMessage() {
        CategoryReport report = new CategoryReport();

        String output = report.generateReport(List.of());

        assertTrue(output.contains("No transactions."));
    }

    @Test
    void testFormattingLineBreaks() {
        CategoryReport report = new CategoryReport();

        List<Transaction> tx = List.of(
                new FoodTransaction(10, "meal", LocalDate.now())
        );

        String output = report.generateReport(tx);

        assertTrue(output.startsWith("=== Spending by Category ===\n"));
        assertTrue(output.endsWith("\n")); // last newline exists
    }
}