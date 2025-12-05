package org.PersonalFinanceTracker.exporter;

import org.PersonalFinanceTracker.transaction.Transaction;

import java.io.FileWriter;
import java.util.List;

public class JsonExporter extends AbstractExporter {

//    @Override
//    protected String serialize(List<Transaction> transactions) {
//        Gson gson = new Gson();
//        return gson.toJson(transactions);
//    }

    @Override
    public String serialize(List<Transaction> transactions) {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");

        for (int i = 0; i < transactions.size(); i++) {
            Transaction t = transactions.get(i);

            sb.append("  {\n");
            sb.append("    \"date\": \"").append(t.getDate().toString()).append("\",\n");
            sb.append("    \"amount\": ").append(t.getAmount()).append(",\n");
            sb.append("    \"category\": \"").append(t.getCategory()).append("\",\n");
            sb.append("    \"description\": \"").append(t.getDescription()).append("\"\n");
            sb.append("  }");

            if (i < transactions.size() - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }

        sb.append("]");
        return sb.toString();
    }

    @Override
    protected void write(String content, String outputPath) throws Exception {
        try (FileWriter writer = new FileWriter(outputPath)) {
            writer.write(content);
        }
    }
}

