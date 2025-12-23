package model.state;

import model.Account;

public interface AccountState {
    void deposit(Account account, double amount);
    void withdraw(Account account, double amount);
    String getStatusName(
        
    );
}
