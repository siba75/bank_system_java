package service.menu;

//package service;

import model.*;
import service.StorageService;
import service.account.AccountFactory;
import decorator.*;
import decorator.OverdraftProtection;
import decorator.PremiumServices;

import java.util.Map;
import java.util.Scanner;

public class BankingService {

    private final Map<String, Account> accounts;

    public BankingService(Map<String, Account> accounts) {
        this.accounts = accounts;
    }

    public Account getOrCreateAccount(Customer customer, Scanner sc) {

        for (Account acc : accounts.values()) {
            if (acc.getCustomer().getEmail().equals(customer.getEmail())) {
                return acc;
            }
        }

        System.out.println("No account found. Creating new account...");
        System.out.println("1. Savings\n2. Current\n3. Loan\n4. Investment");
        System.out.print("Choice: ");
        String choice = sc.nextLine();

        String type = switch (choice) {
            case "2" -> "current";
            case "3" -> "loan";
            case "4" -> "investment";
            default -> "savings";
        };

        Account account = AccountFactory.createAccount(type, customer);

        account.registerObserver(new EmailNotifier(customer.getEmail()));

        if (type.equals("savings")) {
            account = new OverdraftProtection(account);
            account = new PremiumServices(account);
        }

        accounts.put(account.getAccountNumber(), account);
        StorageService.saveAccounts(accounts);
        return account;
    }

    public void deposit(Account acc, double amount) {
        acc.deposit(amount);
        StorageService.saveAccounts(accounts);
    }

    public void withdraw(Account acc, double amount) {
        acc.withdraw(amount);
        StorageService.saveAccounts(accounts);
    }

    public void transfer(Account from, String targetAccNumber, double amount) {
        Account target = accounts.get(targetAccNumber);
        if (target == null) {
            System.out.println("Target account not found.");
            return;
        }
        from.transfer(target, amount);
        StorageService.saveAccounts(accounts);
    }

    public void showAllAccounts() {
        for (Account acc : accounts.values()) {
            System.out.println("--------------------------");
            acc.displayInfo();
        }
    }
}

