// package model.state;

// import model.Account;

// public class FrozenState implements AccountState {

//     @Override
//     public void deposit(Account account, double amount) {
//         System.out.println("Account is frozen. Operation not allowed.");
//     }

//     @Override
//     public void withdraw(Account account, double amount) {
//         System.out.println("Account is frozen. Operation not allowed.");
//     }

//     @Override
//     public void transfer(Account from, Account to, double amount) {
//         System.out.println("Account is frozen. Operation not allowed.");
//     }

//     @Override
//     public String getName() {
//         return "Frozen";
//     }
// }


package model.state;

import model.Account;

public class FrozenState implements AccountState {

    @Override
    public void deposit(Account account, double amount) {
        System.out.println("Account is frozen. Deposit not allowed.");
    }

    @Override
    public void withdraw(Account account, double amount) {
        System.out.println("Account is frozen. Withdrawal not allowed.");
    }

    @Override
    public String getStatusName() {
        return "Frozen";
    }
}
