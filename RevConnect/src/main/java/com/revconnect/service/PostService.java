package com.revconnect.service;

import com.revconnect.dao.PostDao;
import com.revconnect.model.Post;

import java.util.List;

public class PostService {

    private final PostDao dao = new PostDao();

    public boolean createPost(Post post) {
        return dao.createPost(post);
    }

    public List<Post> getMyPosts(int userId) {
        return dao.getPostsByUser(userId);
    }

    public boolean deletePost(int postId, int userId) {
        return dao.deletePost(postId, userId);
    }

    public boolean updatePost(int postId, int userId, String content, String hashtags) {
        return dao.updatePost(postId, userId, content, hashtags);
    }
}
