package model;

/**
 * Observer Pattern
 * هذا الكلاس يمثل إشعارات البريد الإلكتروني
 */
public class EmailNotifier implements AccountObserver {
    private String email;

    public EmailNotifier(String email) { this.email = email; }

    @Override
    public void update(String message) {
        System.out.println("Email to " + email + ": " + message);
    }
}
