package model;

public class InvestmentAccount extends Account {
    public InvestmentAccount(Customer customer) {
        super(customer);
    }

    @Override
    public void displayInfo() {
        System.out.println("Investment Account: " + getAccountNumber() + ", Balance: " + getBalance());
    }
}
