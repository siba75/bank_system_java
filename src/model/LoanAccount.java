package model;

public class LoanAccount extends Account {
    public LoanAccount(Customer customer) {
        super(customer);
    }

    @Override
    public void displayInfo() {
        System.out.println("Loan Account: " + getAccountNumber() + ", Balance: " + getBalance());
    }
}
