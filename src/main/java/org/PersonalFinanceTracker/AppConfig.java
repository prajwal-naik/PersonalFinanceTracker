package org.PersonalFinanceTracker;

public class AppConfig {
    private static AppConfig instance;
    private String currency = "USD";
    private AppConfig() {}

    public static synchronized AppConfig getInstance() {
        if (instance == null) instance = new AppConfig();
        return instance;
    }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
}

