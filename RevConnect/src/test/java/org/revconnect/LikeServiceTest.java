package org.revconnect;

import com.revconnect.model.Post;
import com.revconnect.model.User;
import com.revconnect.service.LikeService;
import com.revconnect.service.PostService;
import com.revconnect.service.UserService;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class LikeServiceTest {

    UserService userService = new UserService();
    PostService postService = new PostService();
    LikeService likeService = new LikeService();

    @Test
    void testLikeAndUnlike() {
        String unique = UUID.randomUUID().toString().substring(0, 8);

        User user = new User();
        user.setUsername("like_" + unique);
        user.setEmail("like_" + unique + "@mail.com");
        user.setPassword("1234");
        user.setUserType("PERSONAL");

        userService.register(user);
        User loggedIn = userService.login(user.getUsername(), "1234");

        Post post = new Post();
        post.setUserId(loggedIn.getId());
        post.setContent("Like test post");
        post.setHashtags("#like");

        postService.createPost(post);

        int postId = postService.getMyPosts(loggedIn.getId()).get(0).getId();

        assertDoesNotThrow(() -> likeService.like(loggedIn.getId(), postId));
        assertDoesNotThrow(() -> likeService.unlike(loggedIn.getId(), postId));
    }
}

