package org.PersonalFinanceTracker.transaction;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionStorage {

    private final String filePath;
    private final TransactionFactory transactionFactory;

    public TransactionStorage(String filePath, TransactionFactory transactionFactory) {
        this.filePath = filePath;
        this.transactionFactory = transactionFactory;
    }

    public void save(List<Transaction> transactions) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Transaction t : transactions) {
                writer.write(t.getDate() + "," + t.getAmount() + "," +
                        t.getCategory() + "," + t.getDescription());
                writer.newLine();
            }
        }
    }

    public List<Transaction> load() throws IOException {
        List<Transaction> transactions = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) return transactions;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 4);
                if (parts.length < 4) continue;

                LocalDate date = LocalDate.parse(parts[0]);
                double amount = Double.parseDouble(parts[1]);
                String category = parts[2];
                String description = parts[3];

                transactions.add(transactionFactory.createTransaction(category, amount, description, date));
            }
        }

        return transactions;
    }
}

