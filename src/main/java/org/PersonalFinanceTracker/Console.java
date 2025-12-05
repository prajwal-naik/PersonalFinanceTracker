package org.PersonalFinanceTracker;

import org.PersonalFinanceTracker.exporter.CsvExporter;
import org.PersonalFinanceTracker.exporter.JsonExporter;
import org.PersonalFinanceTracker.io.IO;
import org.PersonalFinanceTracker.transaction.Transaction;
import org.PersonalFinanceTracker.exporter.ExportService;
import org.PersonalFinanceTracker.transaction.TransactionManager;
import org.PersonalFinanceTracker.observer.TransactionObserver;
import org.PersonalFinanceTracker.report.*;
import org.PersonalFinanceTracker.transaction.TransactionFactory;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Console implements TransactionObserver {
    private final IO io;
    private final TransactionManager manager;
    private final TransactionFactory factory;
    private final ReportService reportService;
    private final ExportService exportService;
    private final Scanner scanner = new Scanner(System.in);

    public Console(
            IO io,
            TransactionManager manager,
            TransactionFactory factory,
            ReportService reportService,
            ExportService exportService) {
        this.io = io;
        this.manager = manager;
        this.factory = factory;
        this.reportService = reportService;
        this.exportService = exportService;
    }

    public void start() {
        manager.addObserver(this);
        boolean running = true;
        while (running) {
            printMenu();
            String choice = io.readLine();
            if (choice == null) {
                break;
            }
            choice = choice.trim();
            switch (choice) {
                case "1": addTransactionFlow(); break;
                case "2": listTransactions(); break;
                case "3": runReports(); break;
                case "4": exportData(); break;
                case "5": settings(); break;
                case "0": running = false; break;
                default: System.out.println("Unknown option");
            }
        }
        System.out.println("Goodbye.");
    }

    private void printMenu() {
        System.out.println("\n=== Finance Tracker ===");
        System.out.println("1) Add transaction");
        System.out.println("2) List transactions");
        System.out.println("3) Reports");
        System.out.println("4) Export (JSON/CSV)");
        System.out.println("5) Settings");
        System.out.println("0) Exit");
        System.out.print("> ");
    }

    private void addTransactionFlow() {
        try {
            System.out.print("Category (food, rent, transport, other or custom): ");
            String cat = io.readLine().trim();
            System.out.print("Amount (positive for expense, negative for income): ");
            double amt = Double.parseDouble(io.readLine().trim());
            System.out.print("Description: ");
            String desc = io.readLine().trim();
            System.out.print("Date (YYYY-MM-DD) or blank for today: ");
            String dateStr = io.readLine().trim();
            LocalDate date = dateStr.isEmpty() ? LocalDate.now() : LocalDate.parse(dateStr);

            Transaction t = factory.createTransaction(cat, amt, desc, date);
            manager.addTransaction(t);
            System.out.println("Added: " + t);
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount.");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listTransactions() {
        List<Transaction> txns = manager.getAllTransactions();
        if (txns.isEmpty()) {
            System.out.println("No transactions.");
            return;
        }
        System.out.println("Transactions:");
        for (int i = 0; i < txns.size(); i++) {
            System.out.printf("%d) %s\n", i + 1, txns.get(i));
        }
        System.out.printf("Total: %.2f %s\n", manager.getTotal(), AppConfig.getInstance().getCurrency());
    }

    private void runReports() {
        System.out.println("Choose report: 1) Monthly 2) Category 3) Income vs Expense");
        String c = io.readLine().trim();
        switch (c) {
            case "1": reportService.setStrategy(new MonthlyReport()); break;
            case "2": reportService.setStrategy(new CategoryReport()); break;
            case "3": reportService.setStrategy(new IncomeExpenseReport()); break;
            default: System.out.println("Unknown report"); return;
        }
        String out = reportService.runReport(manager.getAllTransactions());
        System.out.println(out);
    }

    private void exportData() {
        System.out.println("Export format: 1) JSON 2) CSV");
        String c = io.readLine().trim();
        System.out.print("Filename: ");
        String filename = io.readLine().trim();
        if (filename.isEmpty()) {
            System.out.println("Filename required");
            return;
        }
        try {
            switch (c) {
                case "1": exportService.setStrategy(new JsonExporter()); break;
                case "2": exportService.setStrategy(new CsvExporter()); break;
                default: System.out.println("Unknown format"); return;
            }
            exportService.export(manager.getAllTransactions(), filename);
            System.out.println("Exported to " + filename);
        } catch (Exception e) {
            System.out.println("Export failed: " + e.getMessage());
        }
    }

    private void settings() {
        System.out.println("Settings:");
        System.out.println("1) Currency (current: " + AppConfig.getInstance().getCurrency() + ")");
        System.out.println("0) Back");
        System.out.print("> ");
        String c = io.readLine().trim();
        if ("1".equals(c)) {
            System.out.print("Enter currency (e.g., USD, INR): ");
            String cur = scanner.nextLine().trim();
            if (!cur.isEmpty()) AppConfig.getInstance().setCurrency(cur);
            System.out.println("Currency set to " + AppConfig.getInstance().getCurrency());
        }
    }

    @Override
    public void onTransactionAdded(Transaction t) {
        System.out.println("[UI] Transaction added: " + t.getCategory() + " " + t.getAmount());
    }
}
