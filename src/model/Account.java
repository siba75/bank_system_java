// package model;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.Map;

// import service.StorageService;
// import strategy.InterestStrategy;

// public abstract class Account implements AccountComponent, AccountSubject {





//     private String accountNumber;
//     private double balance;
//     private String status;
//     private Customer customer;
//     private List<AccountObserver> observers = new ArrayList<>();
//     private List<String> transactionHistory = new ArrayList<>();
//     private InterestStrategy interestStrategy;




//  public String toCSV() {
//         return accountNumber + "," + balance + "," + status + "," + customer.getName() + "," + customer.getEmail();
//     }

//     // Create an account object from a CSV line
//     public static Account fromCSV(String csvLine) {
//         String[] parts = csvLine.split(",");
//         String accountNumber = parts[0];
//         double balance = Double.parseDouble(parts[1]);
//         String status = parts[2];
//         String customerName = parts[3];
//         String customerEmail = parts[4];

//         // Example: Create a SavingsAccount (you can extend this for other account types)
//         Customer customer = new Customer(customerName, customerEmail, "123456");
//         Account account = new SavingsAccount(customer);
//         account.accountNumber = accountNumber;
//         account.balance = balance;
//         account.status = status;
//         return account;
//     }


//     public Account(Customer customer) {
//         this.customer = customer;
//         this.balance = 0;
//         this.status = "Active";
//         this.accountNumber = "ACC" + System.currentTimeMillis();
//     }





//     public abstract void displayInfo();

//     // معاملات الحساب
//     public void deposit(double amount) {
//         if (amount <= 0) return;
//         balance += amount;
//         transactionHistory.add("Deposit: " + amount);
//         notifyObservers("Deposited " + amount + " to account " + accountNumber);
//     }

//     public void withdraw(double amount) {
//         if (amount <= 0) return;
//         if (amount > balance) {
//             notifyObservers("Failed withdrawal of " + amount + " from account " + accountNumber + ": insufficient funds");
//         } else {
//             balance -= amount;
//             transactionHistory.add("Withdraw: " + amount);
//             notifyObservers("Withdrew " + amount + " from account " + accountNumber);
//         }
//     }

//     public void applyInterest() {
//         if (interestStrategy != null) {
//             double interest = interestStrategy.calculateInterest(balance);
//             deposit(interest);
//             transactionHistory.add("Interest Applied: " + interest);
//         }
//     }

//     public void transfer(Account targetAccount, double amount) {
//         if (amount <= 0) {
//             System.out.println("Invalid transfer amount.");
//             return;
//         }
//         if (amount > balance) {
//             System.out.println("Insufficient funds for transfer.");
//             return;
//         }

//         this.withdraw(amount);
//         targetAccount.deposit(amount);

//         // Update transaction history
//         transactionHistory.add("Transferred: " + amount + " to account " + targetAccount.getAccountNumber());
//         notifyObservers("Transferred " + amount + " to account " + targetAccount.getAccountNumber());

//         // Save changes to CSV
//         Map<String, Account> accounts = StorageService.loadAccounts();
//         accounts.put(this.getAccountNumber(), this);
//         accounts.put(targetAccount.getAccountNumber(), targetAccount);
//         StorageService.saveAccounts(accounts);
//     }

//     // Observer Pattern
//     public void registerObserver(AccountObserver observer) { observers.add(observer); }
//     public void removeObserver(AccountObserver observer) { observers.remove(observer); }
//     public void notifyObservers(String message) { 
//         for (AccountObserver observer : observers) { observer.update(message); }
//     }

//     // Getters & Setters
//     public double getBalance() { return balance; }
//     public Customer getCustomer() { return customer; }
//     public String getStatus() { return status; }
//     public void setStatus(String status) { this.status = status; }
//     public String getAccountNumber() { return accountNumber; }
//     public List<String> getTransactionHistory() { return transactionHistory; }
//     public void setInterestStrategy(InterestStrategy strategy) { this.interestStrategy = strategy; }
// }
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.state.AccountState;
import model.state.ActiveState;
import model.state.ClosedState;
import model.state.FrozenState;
import service.StorageService;
import strategy.InterestStrategy;

public abstract class Account implements AccountComponent, AccountSubject {

    private String accountNumber;
    private double balance;

    // ❌ لم نحذف status – فقط لم نعد نستخدمه مباشرة
    private String status;

    // ✅ State Pattern
    private AccountState state;

    private Customer customer;
    private List<AccountObserver> observers = new ArrayList<>();
    private List<String> transactionHistory = new ArrayList<>();
    private InterestStrategy interestStrategy;

    /* ================= CSV ================= */

    public String toCSV() {
        return accountNumber + "," + balance + "," + getStatus() + "," +
                customer.getName() + "," + customer.getEmail();
    }

    public static Account fromCSV(String csvLine) {
        String[] parts = csvLine.split(",");
        String accountNumber = parts[0];
        double balance = Double.parseDouble(parts[1]);
        String status = parts[2];
        String customerName = parts[3];
        String customerEmail = parts[4];

        Customer customer = new Customer(customerName, customerEmail, "123456");
        Account account = new SavingsAccount(customer);

        account.accountNumber = accountNumber;
        account.balance = balance;
        account.setStateByName(status);

        return account;
    }

    /* ================= Constructor ================= */

    public Account(Customer customer) {
        this.customer = customer;
        this.balance = 0;
        this.accountNumber = "ACC" + System.currentTimeMillis();

        // الحالة الافتراضية
        this.state = new ActiveState();
        this.status = state.getStatusName();
    }

    public abstract void displayInfo();

    /* ================= State-based Operations ================= */

    public void deposit(double amount) {
        state.deposit(this, amount);
    }

    public void withdraw(double amount) {
        state.withdraw(this, amount);
    }

    // العمليات الحقيقية (تُستدعى من الحالة)
    public void performDeposit(double amount) {
        if (amount <= 0) return;
        balance += amount;
        transactionHistory.add("Deposit: " + amount);
        notifyObservers("Deposited " + amount + " to account " + accountNumber);
    }

    public void performWithdraw(double amount) {
        if (amount <= 0) return;
        if (amount > balance) {
            notifyObservers("Failed withdrawal: insufficient funds");
        } else {
            balance -= amount;
            transactionHistory.add("Withdraw: " + amount);
            notifyObservers("Withdrew " + amount + " from account " + accountNumber);
        }
    }

    /* ================= Interest ================= */

    public void applyInterest() {
        if (interestStrategy != null) {
            double interest = interestStrategy.calculateInterest(balance);
            deposit(interest);
            transactionHistory.add("Interest Applied: " + interest);
        }
    }

    /* ================= Transfer ================= */

    public void transfer(Account targetAccount, double amount) {
        if (amount <= 0 || amount > balance) return;

        this.withdraw(amount);
        targetAccount.deposit(amount);

        transactionHistory.add("Transferred: " + amount +
                " to account " + targetAccount.getAccountNumber());

        notifyObservers("Transferred " + amount +
                " to account " + targetAccount.getAccountNumber());

        Map<String, Account> accounts = StorageService.loadAccounts();
        accounts.put(this.getAccountNumber(), this);
        accounts.put(targetAccount.getAccountNumber(), targetAccount);
        StorageService.saveAccounts(accounts);
    }

    /* ================= Observer ================= */

    public void registerObserver(AccountObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(AccountObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String message) {
        for (AccountObserver observer : observers) {
            observer.update(message);
        }
    }

    /* ================= State Management ================= */

    public void setState(AccountState state) {
        this.state = state;
        this.status = state.getStatusName();
    }

    // مهم للـ CSV
    public void setStateByName(String stateName) {
        // if (stateName.equalsIgnoreCase("Active")) {
        //     setState(new ActiveState());
        // }

        switch (stateName.toLowerCase()) {
        case "active" -> setState(new ActiveState());
        case "frozen" -> setState(new FrozenState());
        case "closed" -> setState(new ClosedState());
        // case "suspended" -> setState(new SuspendedState());
        default -> setState(new ActiveState());
    }
        // يمكنك إضافة Frozen / Closed هنا
    }

    /* ================= Getters ================= */

    public double getBalance() { return balance; }
    public Customer getCustomer() { return customer; }
    public String getAccountNumber() { return accountNumber; }
    public List<String> getTransactionHistory() { return transactionHistory; }
    public void setInterestStrategy(InterestStrategy strategy) { this.interestStrategy = strategy; }

    // ❗ ما زال موجودًا لكن يعتمد على State
    public String getStatus() {
        return state.getStatusName();
    }
}
