package test;

import model.*;
import service.account.AccountFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FactoryTests {

    @Test
    void createSavingsAccount() {
        Customer c = new Customer("S", "s@mail.com", "1");
        Account acc = AccountFactory.createAccount("savings", c);

        assertTrue(acc instanceof SavingsAccount);
    }

    @Test
    void createLoanAccount() {
        Customer c = new Customer("S", "s@mail.com", "1");
        Account acc = AccountFactory.createAccount("loan", c);

        assertTrue(acc instanceof LoanAccount);
    }

    @Test
    void invalidAccountTypeThrowsException() {
        Customer c = new Customer("S", "s@mail.com", "1");
        assertThrows(IllegalArgumentException.class, () -> {
            AccountFactory.createAccount("invalid", c);
        });
    }

}
