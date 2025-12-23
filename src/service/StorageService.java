package service;

import model.Account;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class StorageService {

    private static final String ACCOUNTS_FILE = "accounts.csv";

    // Save accounts to CSV file
    public static void saveAccounts(Map<String, Account> accounts) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ACCOUNTS_FILE))) {
            for (Account account : accounts.values()) {
                writer.println(account.toCSV());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load accounts from CSV file
    public static Map<String, Account> loadAccounts() {
        Map<String, Account> accounts = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Account account = Account.fromCSV(line);
                accounts.put(account.getAccountNumber(), account);
            }
        } catch (IOException e) {
            System.out.println("No existing accounts found. Starting fresh.");
        }
        return accounts;
    }
}