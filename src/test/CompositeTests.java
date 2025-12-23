package test;

import model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CompositeTests {

    @Test
    void compositeHoldsAccounts() {
        Customer c = new Customer("S", "s@mail.com", "1");
        Account a1 = new SavingsAccount(c);
        Account a2 = new CurrentAccount(c);

        CompositeAccount comp = new CompositeAccount("Test");
        comp.addAccount(a1);
        comp.addAccount(a2);

        assertDoesNotThrow(comp::displayInfo);
    }
}
