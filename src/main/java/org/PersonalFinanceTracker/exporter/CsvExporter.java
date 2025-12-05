package org.PersonalFinanceTracker.exporter;


import org.PersonalFinanceTracker.transaction.Transaction;

import java.io.FileWriter;
import java.util.List;

public class CsvExporter extends AbstractExporter {

    @Override
    protected String serialize(List<Transaction> transactions) {
        StringBuilder sb = new StringBuilder("date,amount,category\n");

        for (Transaction t : transactions) {
            sb.append(t.getDate()).append(",")
                    .append(t.getAmount()).append(",")
                    .append(t.getCategory()).append("\n");
        }

        return sb.toString();
    }

    @Override
    protected void write(String content, String outputPath) throws Exception {
        try (FileWriter writer = new FileWriter(outputPath)) {
            writer.write(content);
        }
    }
}

