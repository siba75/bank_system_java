package model;

/**
 * Observer Pattern
 * هذه الواجهة لأي كلاس يمكن أن يرسل إشعارات
 */
public interface AccountSubject {
    void registerObserver(AccountObserver observer);
    void removeObserver(AccountObserver observer);
    void notifyObservers(String message);
}
