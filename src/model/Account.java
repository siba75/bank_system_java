package model;

import java.util.ArrayList;
import java.util.List;
import strategy.InterestStrategy;

public abstract class Account implements AccountComponent, AccountSubject {
    private String accountNumber;
    private double balance;
    private String status;
    private Customer customer;
    private List<AccountObserver> observers = new ArrayList<>();
    private List<String> transactionHistory = new ArrayList<>();
    private InterestStrategy interestStrategy;

    public Account(Customer customer) {
        this.customer = customer;
        this.balance = 0;
        this.status = "Active";
        this.accountNumber = "ACC" + System.currentTimeMillis();
    }

    public abstract void displayInfo();

    // معاملات الحساب
    public void deposit(double amount) {
        if (amount <= 0) return;
        balance += amount;
        transactionHistory.add("Deposit: " + amount);
        notifyObservers("Deposited " + amount + " to account " + accountNumber);
    }

    public void withdraw(double amount) {
        if (amount <= 0) return;
        if (amount > balance) {
            notifyObservers("Failed withdrawal of " + amount + " from account " + accountNumber + ": insufficient funds");
        } else {
            balance -= amount;
            transactionHistory.add("Withdraw: " + amount);
            notifyObservers("Withdrew " + amount + " from account " + accountNumber);
        }
    }

    public void applyInterest() {
        if (interestStrategy != null) {
            double interest = interestStrategy.calculateInterest(balance);
            deposit(interest);
            transactionHistory.add("Interest Applied: " + interest);
        }
    }

    // Observer Pattern
    public void registerObserver(AccountObserver observer) { observers.add(observer); }
    public void removeObserver(AccountObserver observer) { observers.remove(observer); }
    public void notifyObservers(String message) { 
        for (AccountObserver observer : observers) { observer.update(message); }
    }

    // Getters & Setters
    public double getBalance() { return balance; }
    public Customer getCustomer() { return customer; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getAccountNumber() { return accountNumber; }
    public List<String> getTransactionHistory() { return transactionHistory; }
    public void setInterestStrategy(InterestStrategy strategy) { this.interestStrategy = strategy; }
}
