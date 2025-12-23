package test;

import service.AuthService;
import org.junit.jupiter.api.Test;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

public class AuthServiceTests {

    @Test
    void adminLoginDetected() {
        Scanner sc = new Scanner(
                "ADMIN\n" +
                        "admin@bank.com\n" +
                        "admin123\n");

        String role = AuthService.login(sc);
        assertEquals("ADMIN", role);
    }

    @Test
    void customerLoginDetected() {
        Scanner sc = new Scanner(
                "CUSTOMER\n" +
                        "siba@example.com\n" +
                        "123456\n");

        String role = AuthService.login(sc);
        assertEquals("CUSTOMER", role);
    }
}
