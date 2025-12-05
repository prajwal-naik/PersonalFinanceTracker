package org.PersonalFinanceTracker.report;

import org.PersonalFinanceTracker.transaction.Transaction;

import java.util.List;

public interface ReportStrategy {
    String generateReport(List<Transaction> transactions);
}

