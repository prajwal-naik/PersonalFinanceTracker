package org.PersonalFinanceTracker;

import org.PersonalFinanceTracker.exporter.ExportService;
import org.PersonalFinanceTracker.io.IO;
import org.PersonalFinanceTracker.io.SystemIO;
import org.PersonalFinanceTracker.report.ReportService;
import org.PersonalFinanceTracker.transaction.TransactionFactory;
import org.PersonalFinanceTracker.transaction.TransactionManager;

public class Main {
    public static void main(String[] args) {
        TransactionFactory factory = new TransactionFactory();
        TransactionManager manager = new TransactionManager("transactions.txt", factory);
        ReportService reportService = new ReportService();
        ExportService exportService = new ExportService();
        IO io = new SystemIO();

        Console ui = new Console(io, manager, factory, reportService, exportService);
        PersonalFinanceTracker personalFinanceTracker = new PersonalFinanceTracker(ui, manager, factory);

        personalFinanceTracker.addSampleData();
        personalFinanceTracker.start();
    }
}
