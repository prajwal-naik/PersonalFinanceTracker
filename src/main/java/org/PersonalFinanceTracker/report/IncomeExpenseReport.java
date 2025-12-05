package org.PersonalFinanceTracker.report;

import org.PersonalFinanceTracker.transaction.Transaction;

import java.util.List;

public class IncomeExpenseReport implements ReportStrategy {
    @Override
    public String generateReport(List<Transaction> transactions) {
        double expenses = transactions.stream().mapToDouble(Transaction::getAmount).filter(a -> a > 0).sum();
        double income = transactions.stream().mapToDouble(Transaction::getAmount).filter(a -> a < 0).map(Math::abs).sum();

        StringBuilder sb = new StringBuilder();
        sb.append("=== Income vs Expense ===\n");
        sb.append(String.format("Total Income : %.2f\n", income));
        sb.append(String.format("Total Expense: %.2f\n", expenses));
        sb.append(String.format("Net (Income - Expense): %.2f\n", income - expenses));
        return sb.toString();
    }
}

