package org.PersonalFinanceTracker.exporter;

import org.PersonalFinanceTracker.transaction.Transaction;
import java.util.List;

public class ExportService {

    private AbstractExporter exporter;

    public ExportService() {
        this.exporter = new JsonExporter();
    }

    public ExportService(AbstractExporter exporter) {
        this.exporter = exporter;
    }

    public void setStrategy(AbstractExporter exporter) {
        this.exporter = exporter;
    }

    public void export(List<Transaction> transactions, String filePath) throws Exception {
        exporter.export(transactions, filePath);
    }
}

