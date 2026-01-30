package org.revconnect;
import com.revconnect.model.Post;
import com.revconnect.model.User;
import com.revconnect.service.PostService;
import com.revconnect.service.UserService;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class PostServiceTest {

    UserService userService = new UserService();
    PostService postService = new PostService();

    private User createUser() {
        String unique = UUID.randomUUID().toString().substring(0, 8);

        User user = new User();
        user.setUsername("post_" + unique);
        user.setEmail("post_" + unique + "@mail.com");
        user.setPassword("1234");
        user.setUserType("PERSONAL");

        userService.register(user);
        return userService.login(user.getUsername(), "1234");
    }

    @Test
    void testCreatePost() {
        User user = createUser();

        Post post = new Post();
        post.setUserId(user.getId());
        post.setContent("JUnit Post Content");
        post.setHashtags("#junit #test");

        boolean result = postService.createPost(post);
        assertTrue(result);
    }

    @Test
    void testGetMyPosts() {
        User user = createUser();

        List<Post> posts = postService.getMyPosts(user.getId());
        assertNotNull(posts);
    }
}

