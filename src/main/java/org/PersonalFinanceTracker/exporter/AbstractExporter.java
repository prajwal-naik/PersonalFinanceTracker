package org.PersonalFinanceTracker.exporter;

import org.PersonalFinanceTracker.transaction.Transaction;
import java.util.List;

public abstract class AbstractExporter {

    public final void export(List<Transaction> transactions, String outputPath) throws Exception {
        validate(transactions);
        String serialized = serialize(transactions);
        write(serialized, outputPath);
    }

    private void validate(List<Transaction> transactions) {
        if (transactions == null || transactions.isEmpty()) {
            throw new IllegalArgumentException("No transactions available to export.");
        }
    }

    protected abstract String serialize(List<Transaction> transactions) throws Exception;

    protected abstract void write(String content, String outputPath) throws Exception;
}

