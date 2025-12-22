package decorator;
import model.*;

public class PremiumServices extends AccountDecorator {
    public PremiumServices(Account account) { super(account); }

    @Override
    public void displayInfo() {
        account.displayInfo();
        System.out.println("Feature: Premium Services Enabled");
    }

    @Override
    public void deposit(double amount) { account.deposit(amount); }
    @Override
    public void withdraw(double amount) { account.withdraw(amount); }
}
