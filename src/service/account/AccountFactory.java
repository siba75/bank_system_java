package service.account;

import model.*;

public class AccountFactory {

    // Factory method لإنشاء أي نوع حساب
    public static Account createAccount(String type, Customer customer) {
        if (type == null || customer == null) {
            throw new IllegalArgumentException("Type or Customer cannot be null");
        }

        switch (type.toLowerCase()) {
            case "savings":
                return new SavingsAccount(customer);
            case "current":
                return new CurrentAccount(customer);
            case "loan":
                return new LoanAccount(customer);
            case "investment":
                return new InvestmentAccount(customer);
            default:
                throw new IllegalArgumentException("Unknown account type: " + type);
        }
    }
}
