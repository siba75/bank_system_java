package model;

/**
 * AccountDecorator
 * يستخدم Decorator Pattern
 * يسمح بإضافة ميزات اختيارية لأي حساب
 */
public abstract class AccountDecorator extends Account {
    protected Account account; // الحساب الأساسي الذي نضيف له الميزات

    public AccountDecorator(Account account) {
        super(account.getCustomer());
        this.account = account;
    }

    @Override
    public void displayInfo() {
        account.displayInfo(); // عرض معلومات الحساب الأساسي
    }

    // يمكن إضافة ميزات إضافية هنا
}
