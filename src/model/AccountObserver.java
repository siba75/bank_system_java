package model;

/**
 * Observer Pattern
 * هذه الواجهة لأي كلاس يريد استقبال إشعارات الحساب
 */
public interface AccountObserver {
    void update(String message);
}
