package org.PersonalFinanceTracker;

import org.PersonalFinanceTracker.transaction.Transaction;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {
    private static class TestTransaction extends Transaction {
        public TestTransaction(double amount, String desc, LocalDate date) {
            super(amount, desc, date);
        }

        @Override
        public String getCategory() {
            return "TestCategory";
        }
    }

    @Test
    void testGetters() {
        LocalDate date = LocalDate.of(2024, 1, 10);
        Transaction t = new TestTransaction(100.50, "Test description", date);

        assertEquals(100.50, t.getAmount());
        assertEquals("Test description", t.getDescription());
        assertEquals(date, t.getDate());
        assertEquals("TestCategory", t.getCategory());
    }

    @Test
    void testToCsvRowWithoutEscaping() {
        Transaction t = new TestTransaction(
                50.0,
                "Simple",
                LocalDate.of(2024, 2, 5)
        );

        String csv = t.toCsvRow();
        assertEquals("2024-02-05,TestCategory,Simple,50.00", csv);
    }

    @Test
    void testToCsvRowWithEscaping() {
        Transaction t = new TestTransaction(
                75.0,
                "Hello, \"World\"",
                LocalDate.of(2024, 2, 5)
        );

        String csv = t.toCsvRow();
        assertEquals("2024-02-05,TestCategory,\"Hello, \"\"World\"\"\",75.00", csv);
    }

    @Test
    void testToJsonObj() {
        Transaction t = new TestTransaction(
                20.0,
                "A \"quote\" and newline\nhere",
                LocalDate.of(2024, 3, 1)
        );

        String json = t.toJsonObj();
        assertEquals(
                "{\"date\":\"2024-03-01\",\"category\":\"TestCategory\",\"description\":\"A \\\"quote\\\" and newline\\nhere\",\"amount\":20.00}",
                json
        );
    }

    @Test
    void testToStringFormat() {
        Transaction t = new TestTransaction(
                10.0,
                "Lunch",
                LocalDate.of(2024, 5, 10)
        );

        String text = t.toString();
        assertEquals("[2024-05-10] TestCategory : 10.00 (Lunch)", text);
    }
}
