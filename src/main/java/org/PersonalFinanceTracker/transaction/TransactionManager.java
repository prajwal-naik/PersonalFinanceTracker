package org.PersonalFinanceTracker.transaction;

import org.PersonalFinanceTracker.observer.TransactionObserver;
import org.PersonalFinanceTracker.observer.TransactionSubject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TransactionManager implements TransactionSubject {

    private final List<Transaction> transactions;
    private final TransactionStorage storage;
    private final List<TransactionObserver> observers = new ArrayList<>();

    public TransactionManager(String filePath, TransactionFactory transactionFactory) {
        this.storage = new TransactionStorage(filePath, transactionFactory);
        this.transactions = new ArrayList<>();
        loadTransactions();
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        saveTransactions();
        notifyObservers(transaction);
    }

    public double getTotal() {
        return transactions.stream().mapToDouble(Transaction::getAmount).sum();
    }

    public List<Transaction> getAllTransactions() {
        return transactions;
    }

    private void loadTransactions() {
        try {
            List<Transaction> loaded = storage.load();
            if (loaded != null) {
                transactions.addAll(loaded);
            }
        } catch (IOException e) {
            System.out.println("No previous transactions found or failed to load: " + e.getMessage());
        }
    }

    private void saveTransactions() {
        try {
            storage.save(transactions);
        } catch (IOException e) {
            System.out.println("Failed to save transactions: " + e.getMessage());
        }
    }

    @Override
    public void addObserver(TransactionObserver obs) { observers.add(obs); }

    @Override
    public void removeObserver(TransactionObserver obs) { observers.remove(obs); }

    @Override
    public void notifyObservers(Transaction t) {
        for (TransactionObserver obs : observers) obs.onTransactionAdded(t);
    }
}
