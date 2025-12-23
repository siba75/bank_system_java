package test;
import model.Account;
import model.Customer;
import decorator.OverdraftProtection;
import model.SavingsAccount;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AccountTests {

    @Test
    void testDeposit() {
        Customer c = new Customer("Siba", "siba@example.com", "123");
        Account acc = new OverdraftProtection(new SavingsAccount(c));

        acc.deposit(1000);

        assertEquals(1000.0, acc.getBalance());
    }

    @Test
    void testWithdrawOverdraft() {
        Customer c = new Customer("Siba", "siba@example.com", "123");
        Account acc = new OverdraftProtection(new SavingsAccount(c));

        acc.deposit(500);
        acc.withdraw(600);

        assertEquals(500.0, acc.getBalance());
    }
}
