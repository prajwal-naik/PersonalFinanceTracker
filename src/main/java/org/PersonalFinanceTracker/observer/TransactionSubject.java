package org.PersonalFinanceTracker.observer;

import org.PersonalFinanceTracker.transaction.Transaction;

public interface TransactionSubject {
    void addObserver(TransactionObserver obs);
    void removeObserver(TransactionObserver obs);
    void notifyObservers(Transaction t);
}
