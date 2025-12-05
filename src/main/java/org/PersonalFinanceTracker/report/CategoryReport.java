package org.PersonalFinanceTracker.report;

import org.PersonalFinanceTracker.transaction.Transaction;

import java.util.*;
import java.util.stream.Collectors;

public class CategoryReport implements ReportStrategy {
    @Override
    public String generateReport(List<Transaction> transactions) {
        Map<String, Double> perCategory = transactions.stream()
                .collect(Collectors.groupingBy(
                        Transaction::getCategory,
                        Collectors.summingDouble(Transaction::getAmount)
                ));

        StringBuilder sb = new StringBuilder();
        sb.append("=== Spending by Category ===\n");
        perCategory.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue(Comparator.reverseOrder()))
                .forEach(e -> sb.append(String.format("%s : %.2f\n", e.getKey(), e.getValue())));
        if (perCategory.isEmpty()) sb.append("No transactions.\n");
        return sb.toString();
    }
}

