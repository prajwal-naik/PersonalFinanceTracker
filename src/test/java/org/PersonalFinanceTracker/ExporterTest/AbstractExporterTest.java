package org.PersonalFinanceTracker.ExporterTest;


import org.PersonalFinanceTracker.transaction.Transaction;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import org.PersonalFinanceTracker.exporter.AbstractExporter;

import static org.junit.jupiter.api.Assertions.*;

class AbstractExporterTest {

    static class MockExporter extends AbstractExporter {
        boolean serializeCalled = false;
        boolean writeCalled = false;

        @Override
        protected String serialize(List<Transaction> transactions) {
            serializeCalled = true;
            return "MOCK_DATA";
        }

        @Override
        protected void write(String content, String outputPath) {
            writeCalled = true;
        }
    }

    static class StubTransaction extends Transaction {
        public StubTransaction(double amt, String desc, LocalDate date) {
            super(amt, desc, date);
        }
        @Override
        public String getCategory() { return "Test"; }
    }

    @Test
    void testExportCallsSerializeAndWrite() throws Exception {
        MockExporter exporter = new MockExporter();
        List<Transaction> list = List.of(
                new StubTransaction(10.0, "Test", LocalDate.of(2024, 1, 1))
        );

        exporter.export(list, "dummy.txt");

        assertTrue(exporter.serializeCalled);
        assertTrue(exporter.writeCalled);
    }

    @Test
    void testValidateEmptyListThrows() {
        MockExporter exporter = new MockExporter();
        assertThrows(IllegalArgumentException.class, () -> exporter.export(List.of(), "out.txt"));
    }

    @Test
    void testValidateNullThrows() {
        MockExporter exporter = new MockExporter();
        assertThrows(IllegalArgumentException.class, () -> exporter.export(null, "out.txt"));
    }
}

