package org.PersonalFinanceTracker.transaction;

import java.time.LocalDate;

public abstract class Transaction {
    protected double amount;
    protected String description;
    protected LocalDate date;

    public Transaction(double amount, String description, LocalDate date) {
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    public double getAmount() { return amount; }
    public String getDescription() { return description; }
    public LocalDate getDate() { return date; }

    public abstract String getCategory();

    public String toCsvRow() {
        return String.format("%s,%s,%s,%.2f", date.toString(), getCategory(), escapeCsv(description), amount);
    }

    private String escapeCsv(String s) {
        if (s.contains(",") || s.contains("\"") || s.contains("\n")) {
            return "\"" + s.replace("\"", "\"\"") + "\"";
        }
        return s;
    }

    public String toJsonObj() {
        return String.format(
                "{\"date\":\"%s\",\"category\":\"%s\",\"description\":\"%s\",\"amount\":%.2f}",
                date.toString(), getCategory(), escapeJson(description), amount
        );
    }

    private String escapeJson(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n");
    }

    @Override
    public String toString() {
        return String.format("[%s] %s : %.2f (%s)", date.toString(), getCategory(), amount, description);
    }
}

