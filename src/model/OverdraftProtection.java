package model;

/**
 * OverdraftProtection
 * Decorator Pattern
 * يمنع السحب على المكشوف
 */
public class OverdraftProtection extends AccountDecorator {

    public OverdraftProtection(Account account) {
        super(account);
    }

    @Override
    public void displayInfo() {
        account.displayInfo();
        System.out.println("Feature: Overdraft Protection Enabled");
    }

    @Override
    public void withdraw(double amount) {
        if (amount > account.getBalance()) {
            System.out.println("Cannot withdraw " + amount + " due to Overdraft Protection!");
        } else {
            account.withdraw(amount);
        }
    }

    @Override
    public void deposit(double amount) {
        account.deposit(amount); // الإيداع عادي
    }
}
