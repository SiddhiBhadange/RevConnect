package org.revconnect;
import com.revconnect.model.User;
import com.revconnect.service.FollowService;
import com.revconnect.service.UserService;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class FollowServiceTest {

    UserService userService = new UserService();
    FollowService followService = new FollowService();

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
    void testFollowUser() {
        User u1 = createUser("f1");
        User u2 = createUser("f2");

        followService.followUser(u1.getId(), u2.getId());

        List<Integer> following = followService.getFollowing(u1.getId());
        assertTrue(following.contains(u2.getId()));
    }
}
