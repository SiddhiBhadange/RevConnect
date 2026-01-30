package org.revconnect;

import com.revconnect.model.User;
import com.revconnect.service.ConnectionService;
import com.revconnect.service.UserService;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ConnectionServiceTest {

    UserService userService = new UserService();
    ConnectionService connectionService = new ConnectionService();

    private User createUser(String prefix) {
        String unique = UUID.randomUUID().toString().substring(0, 8);

        User user = new User();
        user.setUsername(prefix + "_" + unique);
        user.setEmail(prefix + "_" + unique + "@mail.com");
        user.setPassword("1234");
        user.setUserType("PERSONAL");

        userService.register(user);
        return userService.login(user.getUsername(), "1234");
    }

    @Test
    void testSendConnectionRequest() {
        User u1 = createUser("c1");
        User u2 = createUser("c2");

        boolean result = connectionService.sendRequest(u1.getId(), u2.getId());
        assertTrue(result);
    }
}
