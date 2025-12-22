package decorator;
import model.*;

public class OverdraftProtection extends AccountDecorator {
    public OverdraftProtection(Account account) { super(account); }

    @Override
    public void displayInfo() {
        account.displayInfo();
        System.out.println("Feature: Overdraft Protection Enabled");
    }

    @Override
    public void deposit(double amount) { account.deposit(amount); }

    @Override
    public void withdraw(double amount) {
        if (account.getBalance() - amount < 0) {
            System.out.println("Cannot withdraw: Overdraft Protection!");
        } else { account.withdraw(amount); }
    }
}
