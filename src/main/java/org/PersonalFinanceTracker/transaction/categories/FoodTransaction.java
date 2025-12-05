package org.PersonalFinanceTracker.transaction.categories;

import org.PersonalFinanceTracker.transaction.Transaction;

import java.time.LocalDate;

public class FoodTransaction extends Transaction {
    public FoodTransaction(double amt, String desc, LocalDate date) { super(amt, desc, date); }
    @Override public String getCategory() { return "Food"; }
}

