package org.revconnect;

import com.revconnect.model.User;
import com.revconnect.service.UserService;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    UserService service = new UserService();

    @Test
    void testRegisterUser() {
        User user = new User();

        // Generate unique username & email every run
        String unique = UUID.randomUUID().toString().substring(0, 8);

        user.setUsername("junit_" + unique);
        user.setEmail("junit_" + unique + "@mail.com");
        user.setPassword("1234");
        user.setUserType("PERSONAL");

        boolean result = service.register(user);
        assertTrue(result, "User registration should succeed for unique user");
    }

    @Test
    void testLoginUser() {
        // First create a user for login test
        String unique = UUID.randomUUID().toString().substring(0, 8);

        User user = new User();
        user.setUsername("login_" + unique);
        user.setEmail("login_" + unique + "@mail.com");
        user.setPassword("1234");
        user.setUserType("PERSONAL");

        service.register(user);

        // Now test login
        User loggedIn = service.login(user.getUsername(), "1234");

        if (loggedIn != null) {
            assertEquals(user.getUsername(), loggedIn.getUsername());
        } else {
            fail("Login failed — user not found in database");
        }
    }
}
