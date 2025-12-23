import model.*;
import decorator.Insurance;
import decorator.OverdraftProtection;
import decorator.PremiumServices;
import service.account.AccountFactory;
import service.AuthService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("=================================");
        System.out.println("   BANKING SYSTEM - TERMINAL DEMO ");
        System.out.println("=================================");

        // Customers
        Customer c1 = new Customer("Siba Halawa", "siba@example.com", "123456");
        Customer c2 = new Customer("siba mohammed halawa ", "sibahalawa@example.com", "123456");

        // Accounts (Factory Pattern)
        Account savings = AccountFactory.createAccount("savings", c1);
        Account loan = AccountFactory.createAccount("loan", c1);
        Account current = AccountFactory.createAccount("current", c2);

        // Observer Pattern (Notifications)
        savings.registerObserver(new EmailNotifier(c1.getEmail()));
        loan.registerObserver(new EmailNotifier(c1.getEmail()));
        current.registerObserver(new EmailNotifier(c2.getEmail()));

        // Decorator Pattern
        savings = new OverdraftProtection(savings);
        savings = new PremiumServices(savings);
        loan = new Insurance(loan);

        // Composite Pattern (Admin View)
        CompositeAccount bankOverview = new CompositeAccount("Bank Overview");
        bankOverview.addAccount(savings);
        bankOverview.addAccount(loan);
        bankOverview.addAccount(current);

        // MAIN LOGIN LOOP
        boolean systemRunning = true;

        while (systemRunning) {

            System.out.println("\n========== LOGIN ==========");
            System.out.print("Role (CUSTOMER / ADMIN / EXIT): ");
            String role = sc.nextLine().trim();

            if (role.equalsIgnoreCase("EXIT")) {
                systemRunning = false;
                break;
            }

            System.out.print("Email: ");
            String email = sc.nextLine();

            System.out.print("Password: ");
            String password = sc.nextLine();

            boolean authenticated = AuthService.authenticate(role, email, password);

            if (!authenticated) {
                System.out.println("❌ Authentication failed!");
                continue;
            }

            if (role.equalsIgnoreCase("CUSTOMER")) {
                customerMenu(sc, savings);
                System.out.println("\nCustomer logged out successfully.");
            } 
            else if (role.equalsIgnoreCase("ADMIN")) {
                adminMenu(bankOverview);
                System.out.println("\nAdmin logged out successfully.");
            }
        }

        System.out.println("\nSystem terminated safely.");
        sc.close();
    }

    // ================= CUSTOMER MENU =================
    static void customerMenu(Scanner sc, Account account) {
        int choice;
        do {
            System.out.println("\n========== CUSTOMER MENU ==========");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. View Account Info");
            System.out.println("4. View Transaction History");
            System.out.println("5. Stress Test (Performance)");
            System.out.println("0. Logout");
            System.out.print("Choice: ");

            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> {
                    System.out.print("Amount: ");
                    account.deposit(Double.parseDouble(sc.nextLine()));
                }
                case 2 -> {
                    System.out.print("Amount: ");
                    account.withdraw(Double.parseDouble(sc.nextLine()));
                }
                case 3 -> account.displayInfo();
                case 4 -> System.out.println(account.getTransactionHistory());
                case 5 -> performanceTest(account);
                case 0 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid choice!");
            }

        } while (choice != 0);
    }

    // ================= ADMIN MENU =================
    static void adminMenu(CompositeAccount overview) {
        System.out.println("\n========== ADMIN DASHBOARD ==========");
        overview.displayInfo();
    }

    // ================= PERFORMANCE TEST =================
    static void performanceTest(Account account) {
        System.out.println("\nRunning performance test...");
        for (int i = 0; i < 10; i++) {
            account.deposit(100);
            account.withdraw(50);
        }
        System.out.println("Performance test completed successfully.");
    }
}
