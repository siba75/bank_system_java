package test;

import model.Account;
import model.Customer;
import model.SavingsAccount;
import decorator.PremiumServices;
import decorator.Insurance;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DecoratorTests {

    @Test
    void premiumServicesDoesNotChangeBalance() {
        Customer c = new Customer("Ali", "ali@mail.com", "123");
        Account acc = new PremiumServices(new SavingsAccount(c));

        acc.deposit(500);

        assertEquals(500, acc.getBalance());
    }

    @Test
    void insuranceDoesNotBreakWithdraw() {
        Customer c = new Customer("Ali", "ali@mail.com", "123");
        Account acc = new Insurance(new SavingsAccount(c));

        acc.deposit(400);
        acc.withdraw(100);

        assertEquals(300, acc.getBalance());
    }
}
