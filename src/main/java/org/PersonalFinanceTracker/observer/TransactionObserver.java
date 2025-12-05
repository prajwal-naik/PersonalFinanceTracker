package org.PersonalFinanceTracker.observer;

import org.PersonalFinanceTracker.transaction.Transaction;

public interface TransactionObserver {
    void onTransactionAdded(Transaction t);
}
