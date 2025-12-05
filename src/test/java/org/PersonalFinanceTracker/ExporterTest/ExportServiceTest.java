package org.PersonalFinanceTracker.ExporterTest;



import org.PersonalFinanceTracker.exporter.AbstractExporter;
import org.PersonalFinanceTracker.exporter.CsvExporter;
import org.PersonalFinanceTracker.exporter.ExportService;
import org.PersonalFinanceTracker.transaction.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.*;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExportServiceTest {

    static class StubTransaction extends Transaction {
        public StubTransaction(double amt, String desc, LocalDate date) {
            super(amt, desc, date);
        }
        @Override public String getCategory() { return "Test"; }
    }

    static class MockExporter extends AbstractExporter {
        boolean called = false;
        @Override protected String serialize(List<Transaction> t) { return "X"; }
        @Override protected void write(String c, String p) { called = true; }
    }

    @TempDir
    Path tempDir;

    @Test
    void testExportServiceUsesStrategy() throws Exception {
        MockExporter mock = new MockExporter();
        ExportService service = new ExportService(mock);

        List<Transaction> list = List.of(
                new StubTransaction(30, "Service test", LocalDate.now())
        );

        Path out = tempDir.resolve("file.out");

        service.export(list, out.toString());

        assertTrue(mock.called);
    }

    @Test
    void testSwitchStrategy() throws Exception {
        ExportService service = new ExportService();
        CsvExporter csv = new CsvExporter();
        service.setStrategy(csv);

        Path out = tempDir.resolve("file.csv");
        List<Transaction> list = List.of(
                new StubTransaction(99, "Switch test", LocalDate.now())
        );

        service.export(list, out.toString());

        String content = Files.readString(out);
        assertTrue(content.contains("Test"));
    }
}

