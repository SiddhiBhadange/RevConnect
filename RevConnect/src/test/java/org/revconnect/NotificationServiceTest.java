package org.revconnect;

import com.revconnect.model.Notification;
import com.revconnect.model.User;
import com.revconnect.service.NotificationService;
import com.revconnect.service.UserService;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class NotificationServiceTest {

    UserService userService = new UserService();
    NotificationService notificationService = new NotificationService();

    @Test
    void testCreateAndFetchNotifications() {
        String unique = UUID.randomUUID().toString().substring(0, 8);

        User user = new User();
        user.setUsername("notify_" + unique);
        user.setEmail("notify_" + unique + "@mail.com");
        user.setPassword("1234");
        user.setUserType("PERSONAL");

        userService.register(user);
        User loggedIn = userService.login(user.getUsername(), "1234");

        notificationService.createNotification(
                loggedIn.getId(),
                "JUnit Test Notification",
                "TEST"
        );

        List<Notification> notifications =
                notificationService.getUserNotifications(loggedIn.getId());

        assertFalse(notifications.isEmpty());
    }
}
