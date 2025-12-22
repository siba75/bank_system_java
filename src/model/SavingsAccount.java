package model;

public class SavingsAccount extends Account {
    public SavingsAccount(Customer customer) {
        super(customer);
    }

    @Override
    public void displayInfo() {
        System.out.println("Savings Account: " + getAccountNumber() + ", Balance: " + getBalance());
    }
}
