package org.PersonalFinanceTracker;

import org.PersonalFinanceTracker.transaction.TransactionFactory;
import org.PersonalFinanceTracker.transaction.TransactionManager;

import java.time.LocalDate;

public class PersonalFinanceTracker {
    private final TransactionManager manager;
    private final TransactionFactory factory;
    private final Console ui;

    public PersonalFinanceTracker(Console ui, TransactionManager manager, TransactionFactory factory) {
        this.ui = ui;
        this.manager = manager;
        this.factory = factory;
    }

    public void addSampleData() {
        manager.addTransaction(factory.createTransaction("food", 12.5, "Lunch", LocalDate.now().minusDays(1)));
        manager.addTransaction(factory.createTransaction("rent", 800.0, "Monthly rent", LocalDate.now().withDayOfMonth(1)));
    }

    public void start() {
        ui.start();
    }
}
