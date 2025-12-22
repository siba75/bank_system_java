package service;

public class AuthService {

    // بيانات ثابتة للعرض الأكاديمي
    private static final String ADMIN_EMAIL = "admin@bank.com";
    private static final String ADMIN_PASSWORD = "admin123";

    private static final String CUSTOMER_PASSWORD = "123456";

    /**
     * Authentication method
     * @param role user role (ADMIN / CUSTOMER)
     * @param email user email
     * @param password user password
     * @return true if authenticated successfully
     */
    public static boolean authenticate(String role, String email, String password) {

        // ===== ADMIN AUTH =====
        if (role.equalsIgnoreCase("ADMIN")) {
            return email.equalsIgnoreCase(ADMIN_EMAIL)
                    && password.equals(ADMIN_PASSWORD);
        }

        // ===== CUSTOMER AUTH =====
        if (role.equalsIgnoreCase("CUSTOMER")) {
            // محاكاة تحقق زبون (أي إيميل مع باسورد صحيح)
            return password.equals(CUSTOMER_PASSWORD);
        }

        return false;
    }
}
