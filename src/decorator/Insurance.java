package decorator;
import model.*;

public class Insurance extends AccountDecorator {
    public Insurance(Account account) { super(account); }

    @Override
    public void displayInfo() {
        account.displayInfo();
        System.out.println("Feature: Account Insurance Enabled");
    }

    @Override
    public void deposit(double amount) { account.deposit(amount); }
    @Override
    public void withdraw(double amount) { account.withdraw(amount); }
}
