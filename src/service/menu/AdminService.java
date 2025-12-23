package service.menu;

//package service;

import model.Account;
import service.StorageService;

import java.util.Map;

public class AdminService {

    public boolean changeAccountStatus(String accNumber, String newStatus) {

        Map<String, Account> accounts = StorageService.loadAccounts();
        Account acc = accounts.get(accNumber);

        if (acc == null) return false;

        acc.setStateByName(newStatus);
        accounts.put(accNumber, acc);
        StorageService.saveAccounts(accounts);
        return true;
    }
}
