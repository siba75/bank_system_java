// import model.*;
// import decorator.Insurance;
// import decorator.OverdraftProtection;
// import decorator.PremiumServices;
// import service.account.AccountFactory;
// import service.AuthService;
// import service.StorageService;

// import java.util.Map;
// import java.util.Scanner;

// public class Main {

//     public static void main(String[] args) {

//         Scanner sc = new Scanner(System.in);

//         System.out.println("=================================");
//         System.out.println("   BANKING SYSTEM - TERMINAL DEMO ");
//         System.out.println("=================================");

//         // Load accounts from CSV
//         Map<String, Account> accounts = StorageService.loadAccounts();

//         // ==========================
//         // Customers
//         // ==========================
//         Customer c1 = new Customer("Siba Halawa", "siba@example.com", "123456");
//         Customer c2 = new Customer("Siba Mohammed Halawa", "sibahalawa@example.com", "123456");

//         // ==========================
//         // Accounts (Factory Pattern)
//         // ==========================
//         Account savings = accounts.getOrDefault("ACC1", AccountFactory.createAccount("savings", c1));
//         Account loan = accounts.getOrDefault("ACC2", AccountFactory.createAccount("loan", c1));
//         Account current = accounts.getOrDefault("ACC3", AccountFactory.createAccount("current", c2));

//         // ==========================
//         // Observer Pattern (Notifications)
//         // ==========================
//         savings.registerObserver(new EmailNotifier(c1.getEmail()));
//         loan.registerObserver(new EmailNotifier(c1.getEmail()));
//         current.registerObserver(new EmailNotifier(c2.getEmail()));

//         // ==========================
//         // Decorator Pattern
//         // ==========================
//         savings = new OverdraftProtection(savings);
//         savings = new PremiumServices(savings);
//         loan = new Insurance(loan);

//         // ==========================
//         // Composite Pattern (Admin View)
//         // ==========================
//         CompositeAccount bankOverview = new CompositeAccount("Bank Overview");
//         bankOverview.addAccount(savings);
//         bankOverview.addAccount(loan);
//         bankOverview.addAccount(current);

//         // ==========================
//         // MAIN LOGIN LOOP
//         // ==========================
//         boolean systemRunning = true;

//         while (systemRunning) {

//             System.out.println("\n========== LOGIN ==========");
//             System.out.print("Role (CUSTOMER / ADMIN / EXIT): ");
//             String role = sc.nextLine().trim();

//             if (role.equalsIgnoreCase("EXIT")) {
//                 systemRunning = false;
//                 break;
//             }

//             System.out.print("Email: ");
//             String email = sc.nextLine();

//             System.out.print("Password: ");
//             String password = sc.nextLine();

//             boolean authenticated = AuthService.authenticate(role, email, password);

//             if (!authenticated) {
//                 System.out.println("❌ Authentication failed!");
//                 continue;
//             }

//             if (role.equalsIgnoreCase("CUSTOMER")) {
//                 Customer authenticatedCustomer = AuthService.getAuthenticatedCustomer();
//                 System.out.println("Welcome, " + authenticatedCustomer.getName());
//                 Account customerAccount = accounts.get(authenticatedCustomer.getAccountNumber());
// if (customerAccount == null) {
//     System.out.println("No account found for this customer. Let's create a new account.");
//     System.out.println("Select account type:");
//     System.out.println("1. Savings\n2. Current\n3. Loan\n4. Investment");
//     System.out.print("Choice: ");
//     String typeChoice = sc.nextLine();
//     String type = switch(typeChoice) {
//         case "1" -> "savings";
//         case "2" -> "current";
//         case "3" -> "loan";
//         case "4" -> "investment";
//         default -> "savings";
//     };
//     customerAccount = AccountFactory.createAccount(type, authenticatedCustomer);
//     accounts.put(customerAccount.getAccountNumber(), customerAccount);
//     // Save after creating new account
//     StorageService.saveAccounts(accounts);
//     System.out.println(type.substring(0,1).toUpperCase() + type.substring(1) + " account created.");
// }
//                 customerMenu(sc, customerAccount, accounts);
//             } else if (role.equalsIgnoreCase("ADMIN")) {
//                 adminMenu(bankOverview);
//                 System.out.println("\nAdmin logged out successfully.");
//             }
//         }

//         // Save accounts to CSV
//         accounts.put(savings.getAccountNumber(), savings);
//         accounts.put(loan.getAccountNumber(), loan);
//         accounts.put(current.getAccountNumber(), current);
//         StorageService.saveAccounts(accounts);

//         System.out.println("\nSystem terminated safely.");
//         sc.close();
//     }

//     // ================= CUSTOMER MENU =================
//     static void customerMenu(Scanner sc, Account account, Map<String, Account> accounts) {
//         int choice;
//         do {
//             System.out.println("\n========== CUSTOMER MENU ==========");
//             System.out.println("1. Deposit");
//             System.out.println("2. Withdraw");
//             System.out.println("3. View Account Info");
//             System.out.println("4. View Transaction History");
//             System.out.println("5. Transfer");
//             System.out.println("6. Stress Test (Performance)");
//             System.out.println("0. Logout");
//             System.out.print("Choice: ");

//             choice = Integer.parseInt(sc.nextLine());

//             switch (choice) {
// case 1 -> {
//     System.out.print("Amount: ");
//     account.deposit(Double.parseDouble(sc.nextLine()));
//     // Save after deposit
//     accounts.put(account.getAccountNumber(), account);
//     StorageService.saveAccounts(accounts);
// }
// case 2 -> {
//     System.out.print("Amount: ");
//     account.withdraw(Double.parseDouble(sc.nextLine()));
//     // Save after withdraw
//     accounts.put(account.getAccountNumber(), account);
//     StorageService.saveAccounts(accounts);
// }
//                 case 3 -> account.displayInfo();
//                 case 4 -> System.out.println(account.getTransactionHistory());
//                 case 5 -> {
//                     System.out.print("Target Account Number: ");
//                     String targetAccountNumber = sc.nextLine();
//                     Account targetAccount = accounts.get(targetAccountNumber);
//                     if (targetAccount != null) {
//                         System.out.print("Amount: ");
//                         double amount = Double.parseDouble(sc.nextLine());
//                         account.transfer(targetAccount, amount);
//                     } else {
//                         System.out.println("Target account not found.");
//                     }
//                 }
//                 case 6 -> performanceTest(account);
//                 case 0 -> System.out.println("Logging out...");
//                 default -> System.out.println("Invalid choice!");
//             }

//         } while (choice != 0);
//     }

//     // ================= ADMIN MENU =================
//     static void adminMenu(CompositeAccount overview) {
//     Scanner sc = new Scanner(System.in);
//     boolean adminRunning = true;
//     while (adminRunning) {
//         System.out.println("\n========== ADMIN DASHBOARD ==========");
//         System.out.println("1. View All Customer Accounts");
//         System.out.println("2. Change Account Status");
//         System.out.println("0. Logout");
//         System.out.print("Choice: ");
//         int adminChoice = Integer.parseInt(sc.nextLine());

//         switch (adminChoice) {
//             case 1 -> {
//                 overview.displayInfo();

//                 // Load all accounts
//                 Map<String, model.Account> allAccounts = service.StorageService.loadAccounts();
//                 System.out.println("\n--- ALL CUSTOMER ACCOUNTS ---");
//                 for (model.Account acc : allAccounts.values()) {
//                     System.out.println("-----------------------------");
//                     System.out.println("Account Number: " + acc.getAccountNumber());
//                     System.out.println("Type: " + acc.getClass().getSimpleName());
//                     System.out.println("Owner: " + acc.getCustomer().getName());
//                     System.out.println("Email: " + acc.getCustomer().getEmail());
//                     System.out.println("Balance: " + acc.getBalance());
//                     System.out.println("Status: " + acc.getStatus());
//                 }
//                 System.out.println("-----------------------------");
//             }
//             case 2 -> {
//                 // Change account status
//                 Map<String, model.Account> allAccounts = service.StorageService.loadAccounts();
//                 System.out.print("Enter Account Number: ");
//                 String accNum = sc.nextLine();
//                 model.Account acc = allAccounts.get(accNum);
//                 if (acc != null) {
//     System.out.print("Enter new status (Active/Frozen/Closed): ");
//     String newStatus = sc.nextLine();
//     acc.setStatus(newStatus);
//     allAccounts.put(accNum, acc);
//     // Update the in-memory accounts map (for main save on shutdown)
//     // if (accounts.containsKey(accNum)) {
//     //     accounts.put(accNum, acc);
//     // }
//     service.StorageService.saveAccounts(allAccounts);

//     service.StorageService.saveAccounts(allAccounts);
//     System.out.println("Status for account " + accNum + " updated to " + newStatus + ".");
// } else {
//     System.out.println("Account not found.");
// }
//             }
//             case 0 -> {
//                 adminRunning = false;
//                 System.out.println("Logging out from ADMIN...");
//             }
//             default -> System.out.println("Invalid choice!");
//         }
//     }
// }

//     // ================= PERFORMANCE TEST =================
//     static void performanceTest(Account account) {
//         System.out.println("\nRunning performance test...");
//         for (int i = 0; i < 10; i++) {
//             account.deposit(100);
//             account.withdraw(50);
//         }
//         System.out.println("Performance test completed successfully.");
//     }
// }

import model.*;
import decorator.*;
import service.*;
import service.menu.AdminService;
import service.menu.BankingService;

import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("=================================");
        System.out.println("   BANKING SYSTEM - TERMINAL DEMO ");
        System.out.println("=================================");

        // Load data
        Map<String, Account> accounts = StorageService.loadAccounts();

        BankingService bankingService = new BankingService(accounts);
        AdminService adminService = new AdminService();

        boolean systemRunning = true;

        while (systemRunning) {

            System.out.println("\n========== LOGIN ==========");
            System.out.print("Role (CUSTOMER / ADMIN / EXIT): ");
            String role = sc.nextLine().trim();

            if (role.equalsIgnoreCase("EXIT")) break;

            System.out.print("Email: ");
            String email = sc.nextLine();

            System.out.print("Password: ");
            String password = sc.nextLine();

            if (!AuthService.authenticate(role, email, password)) {
                System.out.println("❌ Authentication failed!");
                continue;
            }

            if (role.equalsIgnoreCase("CUSTOMER")) {
                Customer customer = AuthService.getAuthenticatedCustomer();
                Account account = bankingService.getOrCreateAccount(customer, sc);
                customerMenu(sc, bankingService, account);
            }

            if (role.equalsIgnoreCase("ADMIN")) {
                adminMenu(sc, bankingService, adminService);
            }
        }

        System.out.println("\nSystem terminated safely.");
        sc.close();
    }

    // ================= CUSTOMER MENU =================
    static void customerMenu(Scanner sc, BankingService service, Account account) {

        int choice;
        do {
            System.out.println("\n========== CUSTOMER MENU ==========");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. View Account Info");
            System.out.println("4. View Transaction History");
            System.out.println("5. Transfer");
            System.out.println("0. Logout");
            System.out.print("Choice: ");

            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> {
                    System.out.print("Amount: ");
                    service.deposit(account, Double.parseDouble(sc.nextLine()));
                }
                case 2 -> {
                    System.out.print("Amount: ");
                    service.withdraw(account, Double.parseDouble(sc.nextLine()));
                }
                case 3 -> account.displayInfo();
                case 4 -> System.out.println(account.getTransactionHistory());
                case 5 -> {
                    System.out.print("Target Account Number: ");
                    String target = sc.nextLine();
                    System.out.print("Amount: ");
                    double amount = Double.parseDouble(sc.nextLine());
                    service.transfer(account, target, amount);
                }
                case 0 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 0);
    }

    // ================= ADMIN MENU =================
    static void adminMenu(Scanner sc, BankingService bankingService, AdminService adminService) {

        boolean adminRunning = true;

        while (adminRunning) {
            System.out.println("\n========== ADMIN DASHBOARD ==========");
            System.out.println("1. View All Accounts");
            System.out.println("2. Change Account Status");
            System.out.println("0. Logout");
            System.out.print("Choice: ");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> bankingService.showAllAccounts();
                case 2 -> {
                    System.out.print("Account Number: ");
                    String accNum = sc.nextLine();
                    System.out.print("New Status (Active/Frozen/Closed): ");
                    String status = sc.nextLine();
                    if (adminService.changeAccountStatus(accNum, status)) {
                        System.out.println("Status updated successfully.");
                    } else {
                        System.out.println("Account not found.");
                    }
                }
                case 0 -> adminRunning = false;
                default -> System.out.println("Invalid choice!");
            }
        }
    }
}
