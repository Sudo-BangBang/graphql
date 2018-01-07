package com.sudobangbang.graphql.repository;

import com.sudobangbang.graphql.model.Post;

import java.util.List;

public interface PostRepo {
    List<Post> getAllPosts();
    List<Post> findByBlogId(String blogId);
    List<Post> findByLinkId(String linkId);
    Post findById(String postId);
    Post savePost(Post post);
}
