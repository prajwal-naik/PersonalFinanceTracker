package org.PersonalFinanceTracker.ExporterTest;

import org.PersonalFinanceTracker.exporter.JsonExporter;
import org.PersonalFinanceTracker.transaction.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.*;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonExporterTest {

    static class StubTransaction extends Transaction {
        public StubTransaction(double amt, String desc, LocalDate date) {
            super(amt, desc, date);
        }
        @Override public String getCategory() { return "Rent"; }
    }

    @TempDir
    Path tempDir;

    @Test
    void testJsonSerialization() throws Exception {
        JsonExporter exporter = new JsonExporter();
        List<Transaction> list = List.of(
                new StubTransaction(500, "Monthly rent", LocalDate.of(2024, 1, 1))
        );

        String json = exporter.serialize(list);

        assertTrue(json.contains("\"date\": \"2024-01-01\""));
        assertTrue(json.contains("\"amount\": 500"));
        assertTrue(json.contains("\"category\": \"Rent\""));
        assertTrue(json.contains("\"description\": \"Monthly rent\""));
    }

    @Test
    void testJsonWriteToFile() throws Exception {
        JsonExporter exporter = new JsonExporter();
        Path out = tempDir.resolve("export.json");

        List<Transaction> list = List.of(
                new StubTransaction(20, "Snacks", LocalDate.of(2024, 3, 5))
        );

        exporter.export(list, out.toString());

        assertTrue(Files.exists(out));
        String content = Files.readString(out);
        assertTrue(content.contains("Snacks"));
    }
}