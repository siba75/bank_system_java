package model.state;

import model.Account;

public class ClosedState implements AccountState {

    @Override
    public void deposit(Account account, double amount) {
        System.out.println("Account is closed. No operations allowed.");
    }

    @Override
    public void withdraw(Account account, double amount) {
        System.out.println("Account is closed. No operations allowed.");
    }

    @Override
    public String getStatusName() {
        return "Closed";
    }
}
