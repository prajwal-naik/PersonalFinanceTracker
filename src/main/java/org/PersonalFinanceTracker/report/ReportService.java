package org.PersonalFinanceTracker.report;

import org.PersonalFinanceTracker.transaction.Transaction;

import java.util.List;

public class ReportService {
    private ReportStrategy strategy;

    public ReportService() {}

    public void setStrategy(ReportStrategy strategy) { this.strategy = strategy; }

    public String runReport(List<Transaction> transactions) {
        if (strategy == null) throw new IllegalStateException("Report strategy not set");
        return strategy.generateReport(transactions);
    }
}
