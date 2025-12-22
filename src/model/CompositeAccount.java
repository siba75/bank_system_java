package model;

import java.util.ArrayList;
import java.util.List;

/**
 * CompositeAccount
 * يستخدم Composite Pattern
 * يمثل مجموعة من الحسابات يمكن التعامل معها كحساب واحد
 */
public class CompositeAccount implements AccountComponent {

    private String name;
    private List<AccountComponent> accounts = new ArrayList<>();

    public CompositeAccount(String name) {
        this.name = name;
    }

    public void addAccount(AccountComponent account) {
        accounts.add(account);
    }

    public void removeAccount(AccountComponent account) {
        accounts.remove(account);
    }

    @Override
    public void displayInfo() {
        System.out.println("Composite Account: " + name);
        for (AccountComponent account : accounts) {
            account.displayInfo(); // يعرض كل الحسابات الفرعية
        }
    }
}
