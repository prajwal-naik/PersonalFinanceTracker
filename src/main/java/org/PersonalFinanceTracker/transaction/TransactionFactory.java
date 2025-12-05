package org.PersonalFinanceTracker.transaction;

import org.PersonalFinanceTracker.transaction.categories.FoodTransaction;
import org.PersonalFinanceTracker.transaction.categories.OtherTransaction;
import org.PersonalFinanceTracker.transaction.categories.RentTransaction;
import org.PersonalFinanceTracker.transaction.categories.TransportTransaction;

import java.time.LocalDate;

public class TransactionFactory {
    public Transaction createTransaction(String type, double amt, String desc, LocalDate date) {
        return switch (type.toLowerCase()) {
            case "food" -> new FoodTransaction(amt, desc, date);
            case "rent" -> new RentTransaction(amt, desc, date);
            case "transport" -> new TransportTransaction(amt, desc, date);
            case "other" -> new OtherTransaction("Other", amt, desc, date);
            default ->
                // allow arbitrary categories via OtherTransaction
                    new OtherTransaction(capitalize(type), amt, desc, date);
        };
    }

    private String capitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        return s.substring(0,1).toUpperCase() + s.substring(1);
    }
}

