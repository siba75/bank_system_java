package model;

/**
 * Insurance
 * Decorator Pattern
 * يضيف تأمين للحساب
 */
public class Insurance extends AccountDecorator {

    public Insurance(Account account) {
        super(account);
    }

    @Override
    public void displayInfo() {
        account.displayInfo();
        System.out.println("Feature: Account Insurance Enabled");
    }
}
