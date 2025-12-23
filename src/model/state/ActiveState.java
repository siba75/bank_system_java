package model.state;

import model.Account;

public class ActiveState implements AccountState {

    @Override
    public void deposit(Account account, double amount) {
        account.performDeposit(amount);
    }

    @Override
    public void withdraw(Account account, double amount) {
        account.performWithdraw(amount);
    }

    @Override
    public String getStatusName() {
        return "Active";
    }
}
