package org.PersonalFinanceTracker.report;

import org.PersonalFinanceTracker.transaction.Transaction;

import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

public class MonthlyReport implements ReportStrategy {
    @Override
    public String generateReport(List<Transaction> transactions) {
        Map<YearMonth, Double> perMonth = transactions.stream()
                .collect(Collectors.groupingBy(
                        t -> YearMonth.from(t.getDate()),
                        TreeMap::new,
                        Collectors.summingDouble(Transaction::getAmount)
                ));

        StringBuilder sb = new StringBuilder();
        sb.append("=== Monthly Spending ===\n");
        for (Map.Entry<YearMonth, Double> entry : perMonth.entrySet()) {
            sb.append(String.format("%s : %.2f\n", entry.getKey().toString(), entry.getValue()));
        }
        if (perMonth.isEmpty()) sb.append("No transactions.\n");
        return sb.toString();
    }
}

