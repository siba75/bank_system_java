package service;

import java.util.Scanner;

public class AuthService {

    // بيانات ثابتة للعرض الأكاديمي
    private static final String ADMIN_EMAIL = "admin@bank.com";
    private static final String ADMIN_PASSWORD = "admin123";

    private static final String CUSTOMER_PASSWORD = "123456";

    /**
     * Terminal login handler
     * 
     * @param sc Scanner for user input
     * @return AUTHORIZED ROLE or EXIT
     */
    public static String login(Scanner sc) {

        System.out.println("\n========== LOGIN ==========");
        System.out.print("Role (ADMIN / CUSTOMER / EXIT): ");
        String role = sc.nextLine();

        if (role.equalsIgnoreCase("EXIT")) {
            return "EXIT";
        }

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        boolean authenticated = authenticate(role, email, password);

        if (authenticated) {
            return role.toUpperCase();
        }

        System.out.println("Authentication failed!");
        return "EXIT";
    }

    /**
     * Authentication logic
     */
    public static boolean authenticate(String role, String email, String password) {

        if (role.equalsIgnoreCase("ADMIN")) {
            return email.equalsIgnoreCase(ADMIN_EMAIL)
                    && password.equals(ADMIN_PASSWORD);
        }

        if (role.equalsIgnoreCase("CUSTOMER")) {
            return password.equals(CUSTOMER_PASSWORD);
        }

        return false;
    }
}
