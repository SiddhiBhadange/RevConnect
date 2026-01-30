package org.revconnect;
import com.revconnect.model.Comment;
import com.revconnect.model.Post;
import com.revconnect.model.User;
import com.revconnect.service.CommentService;
import com.revconnect.service.PostService;
import com.revconnect.service.UserService;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CommentServiceTest {

    UserService userService = new UserService();
    PostService postService = new PostService();
    CommentService commentService = new CommentService();

    private User setupUserAndPost(Post postOut) {
        String unique = UUID.randomUUID().toString().substring(0, 8);

        User user = new User();
        user.setUsername("comment_" + unique);
        user.setEmail("comment_" + unique + "@mail.com");
        user.setPassword("1234");
        user.setUserType("PERSONAL");

        userService.register(user);
        User loggedIn = userService.login(user.getUsername(), "1234");

        Post post = new Post();
        post.setUserId(loggedIn.getId());
        post.setContent("Post for comment test");
        post.setHashtags("#comment");

        postService.createPost(post);

        postOut.setUserId(loggedIn.getId());
        return loggedIn;
    }

    @Test
    void testAddAndGetComments() {
        Post post = new Post();
        User user = setupUserAndPost(post);

        List<Post> posts = postService.getMyPosts(user.getId());
        int postId = posts.get(0).getId();

        Comment comment = new Comment();
        comment.setPostId(postId);
        comment.setUserId(user.getId());
        comment.setContent("JUnit test comment");

        boolean added = commentService.addComment(comment);
        assertTrue(added);

        List<Comment> comments = commentService.getCommentsByPost(postId);
        assertFalse(comments.isEmpty());
    }
}

