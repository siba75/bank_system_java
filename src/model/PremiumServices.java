package model;

/**
 * PremiumServices
 * Decorator Pattern
 * يضيف خدمات مميزة للحساب
 */
public class PremiumServices extends AccountDecorator {

    public PremiumServices(Account account) {
        super(account);
    }

    @Override
    public void displayInfo() {
        account.displayInfo();
        System.out.println("Feature: Premium Services Enabled");
    }
}
