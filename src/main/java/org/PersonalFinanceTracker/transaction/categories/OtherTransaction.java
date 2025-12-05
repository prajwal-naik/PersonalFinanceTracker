package org.PersonalFinanceTracker.transaction.categories;

import org.PersonalFinanceTracker.transaction.Transaction;

import java.time.LocalDate;

public class OtherTransaction extends Transaction {
    private final String category;
    public OtherTransaction(String category, double amt, String desc, LocalDate date) {
        super(amt, desc, date);
        this.category = category;
    }
    @Override public String getCategory() { return category; }
}

