package model;

public class CurrentAccount extends Account {
    public CurrentAccount(Customer customer) {
        super(customer);
    }

    @Override
    public void displayInfo() {
        System.out.println("Current Account: " + getAccountNumber() + ", Balance: " + getBalance());
    }
}
